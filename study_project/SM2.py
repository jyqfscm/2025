import binascii
import sys

from gmssl import sm2 as SM2
from random import SystemRandom
from base64 import b64encode, b64decode

from gmssl.func import random_hex


class CurveFp:
    def __init__(self, A, B, P, N, Gx, Gy, name):
        self.A = A
        self.B = B
        self.P = P
        self.N = N
        self.Gx = Gx
        self.Gy = Gy
        self.name = name


class SM2Key:
    sm2p256v1 = CurveFp(
        name="sm2p256v1",
        A=0xFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC,
        B=0x28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93,
        P=0xFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF,
        N=0xFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123,
        Gx=0x32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7,
        Gy=0xBC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0
    )

    @staticmethod
    def multiply(a, n, N, A, P):
        return SM2Key.fromJacobian(SM2Key.jacobianMultiply(SM2Key.toJacobian(a), n, N, A, P), P)

    @staticmethod
    def add(a, b, A, P):
        return SM2Key.fromJacobian(SM2Key.jacobianAdd(SM2Key.toJacobian(a), SM2Key.toJacobian(b), A, P), P)

    @staticmethod
    def inv(a, n):
        if a == 0:
            return 0
        lm, hm = 1, 0
        low, high = a % n, n
        while low > 1:
            r = high // low
            nm, new = hm - lm * r, high - low * r
            lm, low, hm, high = nm, new, lm, low
        return lm % n

    @staticmethod
    def toJacobian(Xp_Yp):
        Xp, Yp = Xp_Yp
        return Xp, Yp, 1

    @staticmethod
    def fromJacobian(Xp_Yp_Zp, P):
        Xp, Yp, Zp = Xp_Yp_Zp
        z = SM2Key.inv(Zp, P)
        return (Xp * z ** 2) % P, (Yp * z ** 3) % P

    @staticmethod
    def jacobianDouble(Xp_Yp_Zp, A, P):
        Xp, Yp, Zp = Xp_Yp_Zp
        if not Yp:
            return 0, 0, 0
        ysq = (Yp ** 2) % P
        S = (4 * Xp * ysq) % P
        M = (3 * Xp ** 2 + A * Zp ** 4) % P
        nx = (M ** 2 - 2 * S) % P
        ny = (M * (S - nx) - 8 * ysq ** 2) % P
        nz = (2 * Yp * Zp) % P
        return nx, ny, nz

    @staticmethod
    def jacobianAdd(Xp_Yp_Zp, Xq_Yq_Zq, A, P):
        Xp, Yp, Zp = Xp_Yp_Zp
        Xq, Yq, Zq = Xq_Yq_Zq
        if not Yp:
            return Xq, Yq, Zq
        if not Yq:
            return Xp, Yp, Zp
        U1 = (Xp * Zq ** 2) % P
        U2 = (Xq * Zp ** 2) % P
        S1 = (Yp * Zq ** 3) % P
        S2 = (Yq * Zp ** 3) % P
        if U1 == U2:
            if S1 != S2:
                return 0, 0, 1
            return SM2Key.jacobianDouble((Xp, Yp, Zp), A, P)
        H = U2 - U1
        R = S2 - S1
        H2 = (H * H) % P
        H3 = (H * H2) % P
        U1H2 = (U1 * H2) % P
        nx = (R ** 2 - H3 - 2 * U1H2) % P
        ny = (R * (U1H2 - nx) - S1 * H3) % P
        nz = (H * Zp * Zq) % P
        return nx, ny, nz

    @staticmethod
    def jacobianMultiply(Xp_Yp_Zp, n, N, A, P):
        Xp, Yp, Zp = Xp_Yp_Zp
        if Yp == 0 or n == 0:
            return (0, 0, 1)
        if n == 1:
            return (Xp, Yp, Zp)
        if n < 0 or n >= N:
            return SM2Key.jacobianMultiply((Xp, Yp, Zp), n % N, N, A, P)
        if (n % 2) == 0:
            return SM2Key.jacobianDouble(SM2Key.jacobianMultiply((Xp, Yp, Zp), n // 2, N, A, P), A, P)
        if (n % 2) == 1:
            mv = SM2Key.jacobianMultiply((Xp, Yp, Zp), n // 2, N, A, P)
            return SM2Key.jacobianAdd(SM2Key.jacobianDouble(mv, A, P), (Xp, Yp, Zp), A, P)


class PrivateKey:
    def __init__(self, curve=SM2Key.sm2p256v1, secret=None):
        self.curve = curve
        self.secret = secret or SystemRandom().randrange(1, curve.N)
        # print("self.secret:",self.secret)

    def PublicKey(self):
        curve = self.curve
        xPublicKey, yPublicKey = SM2Key.multiply((curve.Gx, curve.Gy), self.secret, A=curve.A, P=curve.P, N=curve.N)
        return PublicKey(xPublicKey, yPublicKey, curve)

    def ToString(self):
        return "{}".format(str(hex(self.secret))[2:].zfill(64))


class PublicKey:
    def __init__(self, x, y, curve):
        self.x = x
        self.y = y
        self.curve = curve

    def ToString(self, compressed=True):
        return '04' + {
            True: str(hex(self.x))[2:],
            False: "{}{}".format(str(hex(self.x))[2:].zfill(64), str(hex(self.y))[2:].zfill(64))
        }.get(compressed)


class SM2Util:
    def __init__(self, pub_key=None, pri_key=None):
        self.pub_key = pub_key
        self.pri_key = pri_key
        self.sm2 = SM2.CryptSM2(public_key=self.pub_key, private_key=self.pri_key)

    def Encrypt(self, data):
        info = self.sm2.encrypt(data.encode())
        return b64encode(info).decode()

    def Decrypt(self, data):
        try:
            info = b64decode(data.encode())
            return self.sm2.decrypt(info).decode()
        except Exception as e:
            print("解密失败:", e)
            exit(1)  # Exit the program if decryption fails

    def Sign(self, data):

        random_hex_str = random_hex(self.sm2.para_len)
        """有要求配置ID可配置"""
        # random_hex_str = '1234567812345678'.encode('utf-8').hex()

        self.sm2_crypt = SM2.CryptSM2(public_key=self.pub_key, private_key=self.pri_key)
        sign = self.sm2_crypt.sign_with_sm3(data.encode(), random_hex_str)
        # print('sign:%s' % sign)
        # # 验签
        # verify = self.sm2_crypt.verify_with_sm3(sign, data.encode())
        # print('verify:%s' % verify)

        return sign

    def Verify(self, data, sign):
        self.sm2_crypt = SM2.CryptSM2(public_key=self.pub_key, private_key=self.pri_key)
        return self.sm2_crypt.verify_with_sm3(sign, data.encode())

    @staticmethod
    def GenKeyPair(secret):
        pri = PrivateKey(secret=secret)
        pub = pri.PublicKey()
        return pri.ToString(), pub.ToString(compressed=False)

if __name__ == '__main__':

    choice = sys.argv[1]
    data = sys.argv[2]
    pri_key = sys.argv[3]
    pub_key = sys.argv[4]
    sign = sys.argv[5]
    # choice = "4"
    # data = '044d58d2f1db795b071b04e1861060c304bb9f18643b5d78935de028055e6fe962e6ba1ce3378bad7d6f6559f966c6e82f9089268c384b272250c1f98c721f7331998b059d1c739747d84c79a249258881ec4b7f3cf6e7ab849c4849c50726e5aad82813'
    # pri_key = "7802a84f507b05455745451e9973fc56c338539c773be50a78e85857c445be45"
    # pub_key = "04ced7274d27b4f775735bfa5a6f982a28916a00af01420498da8d73ceb6bdbb5b2576267691c23bb42fb531fa77a3d506572b649d3df3c8a9401e0f8978a1e385"
    # sign = "306b1d6e821328b695b0de6a35958116dc3cf76b3af2d6dbeab180cf0b20d2ccb30b4f9f560e1d06990e2dcbf4f1bb4e02230db3f5655deb6c5db78462cf782b"
    while True:

        # 1. 生成公私钥对
        # 2. 加密功能
        # 3. 签名功能
        # 4. 验签功能
        # 5. 解密功能

        if choice == '1':
            secret_int = None
            e = SM2Util.GenKeyPair(secret_int)
            print('privateKey:{}publicKey:{}'.format(e[0], e[1]))
            break
        elif choice == '2':
            sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)
            cipher = sm2.Encrypt(data)
            print('加密:{}'.format(cipher))
            # print('加密:{}\n解密:{}'.format(cipher, sm2.Decrypt(cipher)))
            break
        elif choice == '3':
            sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)
            sign = sm2.Sign(data)
            print('sign:{}'.format(sign))
            # print('签名:{}\n验签:{}'.format(sign, sm2.Verify(data, sign)))
            break
        elif choice == '4':
            sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)
            # sign = sm2.Sign(data)
            print('verify:{}'.format(sm2.Verify(data, sign)))
            # print('签名:{}\n验签:{}'.format(sign, sm2.Verify(data, sign)))
            break
        elif choice == '5':
            sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)
            cipher = sm2.Encrypt(data)
            print('解密:{}'.format(sm2.Decrypt(cipher)))
            # print('加密:{}\n解密:{}'.format(cipher, sm2.Decrypt(cipher)))
            break
        else:
            print("无效选择，请重试。")


    # # data就是原始数据（明文），对应系统里面每张图片唯一的标识号
    # data = 'hahahaa123233 '
    # print('原数据:{}'.format(data))
    #
    # # 想要生成公私钥对就用secret_int = None，然后e[0]就是私钥，e[1]就是公钥
    # secret_int = None
    # e = SM2Util.GenKeyPair(secret_int)
    # print('私钥:{} 公钥:{}'.format(e[0], e[1]))
    #
    # # 在知道公私钥对之后可以进行加密解密或者签名验签功能，这时就让用户输入对应的公钥私钥(加密时输入对方公钥，签名时输入自己的私钥，验签时输入对方的公钥)
    # pri_key = "1b496c1e5a4f7228853d3f9df3dd7d22ab5a5ba325d73aeba3fc9d56ee750eb3"
    # pub_key = "043b9214059bda2473c33bdc5aeb500f9620bb91a5114ddaae3ba4a8f386a64e7f2a07e4718b567793ca476066e3f1f2a75bcfd70909149056d5ab565d09822b13"
    # sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)
    #
    # # 签名和验签功能：需要输入原始数据data，以及确定sm2（需要公钥与私钥，使用sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)）
    # sign = sm2.Sign(data)
    # print('签名:{} 验签:{}'.format(sign, sm2.Verify(data, sign)))
    #
    # # 加密和解密功能：需要输入原始数据data，以及确定sm2（需要公钥与私钥，使用sm2 = SM2Util(pri_key=pri_key, pub_key=pub_key)），但加密只需要公钥，解密只需要私钥，可以把在使用一方时把另一方随便填一个。
    # cipher = sm2.Encrypt(data)
    # print('加密:{}\n解密:{}'.format(cipher, sm2.Decrypt(cipher)))

