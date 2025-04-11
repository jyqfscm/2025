package com.example.test;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Component
public class watermark {
    public String Add(String url1,String url2,String code,String choice,String filename) {
        String exe = "C:\\Users\\86187\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        String py = "prediction.py";
        String folder = "C:\\Users\\86187\\Desktop\\PIMoG-Improveded-master";

        System.out.println("url1:" + url1);
        System.out.println("url2:" + url2);
        System.out.println("code:" + code);
        System.out.println("choice:" + choice);

        try {
            // 构建进入指定文件夹的命令
            String[] cmdArr = new String[]{"cmd", "/c", "cd", folder, "&&", exe, py, "--mode", choice, "--code", code};
            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);

            // 将进程的输出流和错误流合并
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取进程的输出流
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // 等待进程执行结束
            int exitCode = process.waitFor();
            System.out.println("Python脚本输出：" + result.toString());

            // 复制文件
            copyFile(url2, url1,filename);
            // 返回结果
            return result.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String convertToPng(String filename) {
        // 检查文件名是否以 .png 结尾
        if (filename != null && !filename.toLowerCase().endsWith(".png")) {
            // 获取不包括后缀的文件名部分
            int dotIndex = filename.lastIndexOf(".");
            if (dotIndex != -1) {
                filename = filename.substring(0, dotIndex);
            }
            // 添加 .png 后缀
            filename += ".png";
        }
        return filename;
    }
    private void copyFile(String srcFilePath, String destDirPath,String filename) {
        String sourceDirPath = "C:\\Users\\86187\\Desktop\\PIMoG-Improveded-master\\image\\concatenate_output";
        Path srcPath = Paths.get(srcFilePath);
        String originalfileName = srcPath.getFileName().toString();
        String fileName = convertToPng(originalfileName);
        Path sourceFilePath = Paths.get(sourceDirPath, fileName);
        Path destDir = Paths.get(destDirPath);
        Path destFilePath = destDir.resolve(fileName);

        try {
            // 创建目标目录（如果不存在）
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
            }
            System.out.println(sourceFilePath);
            System.out.println(destFilePath);
            // 复制文件
            Files.copy(sourceFilePath, destFilePath);
            System.out.println("文件复制成功！");
            // 重命名文件
            Path newFilePath = destDir.resolve(filename + ".png");
            Files.move(destFilePath, newFilePath);
            System.out.println("文件重命名成功！");
        } catch (IOException e) {
            System.err.println("文件复制失败：" + e.getMessage());
        }
    }

    private void copydecodeFile(String srcFilePath, String destFilePath, String newFileName) {
        Path srcPath = Paths.get(srcFilePath);
        Path destPath = Paths.get(destFilePath);
        Path destDir = destPath.getParent(); // Get the destination directory

        try {
            // Create the destination directory if it doesn't exist
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
            }

            // Copy the file
            Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully!");

            // Rename the file
            Path newFilePath = destDir.resolve(newFileName);
            Files.move(destPath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed successfully!");
        } catch (IOException e) {
            System.err.println("File operation failed: " + e.getMessage());
        }
    }
    public String decode(String url2,String choice,String demarkcode, String ifmodified) {
        String exe = "C:\\Users\\86187\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        String py = "prediction.py";
        String folder = "C:\\Users\\86187\\Desktop\\PIMoG-Improveded-master";

        System.out.println("url2:" + url2);
        System.out.println("choice:" + choice);
        System.out.println("code:" + demarkcode);
        System.out.println("ifmodified:" + ifmodified);

        if(ifmodified.equals("true")){
            copydecodeFile("C:/Users/86187/Desktop/Screen-Shooting-Resilient-Watermarking-main/temp_image.png","C:/Users/86187/Desktop/PIMoG-Improveded-master/image/concatenate_output/temp_image.png","decode.png");
        }
        try {
            // 构建进入指定文件夹的命令
            String[] cmdArr = new String[]{"cmd", "/c", "cd", folder, "&&", exe, py, "--mode", choice, "--code", demarkcode};
            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);

            // 将进程的输出流和错误流合并
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取进程的输出流
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String target = "Decode result: ";
                if (!line.contains(target)) {
                    continue; // 跳过不包含 "Decode result: " 的行
                }
                // 删除 "Decode result: " 并将剩余内容添加到 result
                result.append(line.substring(line.indexOf(target) + target.length()));
            }


            // 等待进程执行结束
            int exitCode = process.waitFor();
            System.out.println("Python脚本输出：" + result.toString());

            return result.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getarea(String requestBody) {
        String exe = "C:\\Users\\86187\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        String py = "script2.py";
        String folder = "C:\\Users\\86187\\Desktop\\Screen-Shooting-Resilient-Watermarking-main";

        System.out.println("regions:" + requestBody);

        try {
            // 构建进入指定文件夹的命令
            String[] cmdArr = new String[]{"cmd", "/c", "cd", folder, "&&", exe, py, "--area", requestBody};
            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);

            // 将进程的输出流和错误流合并
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取进程的输出流
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String line;
            boolean multiply = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("矩阵") || line.contains("cornerCoords") || line.contains("single")) {
                    continue; // 跳过包含"单矩阵"的行
                }
                if (line.contains("1.0e+03")) {
                    multiply = true; // 如果读取到 scientific notation，则设置 multiply 为 true
                    continue;
                }
                String[] parts = line.trim().split("\\s+");
                if (multiply && parts.length == 2) {
                    // 如果 multiply 为 true，且读取到的行有两个数字，则将数字乘以 1000
                    double x = Double.parseDouble(parts[0]) * 1000;
                    double y = Double.parseDouble(parts[1]) * 1000;
                    result.append(x).append(" ").append(y).append("\n");
                } else {
                    result.append(line).append("\n");
                }
            }

            // 等待进程执行结束
            int exitCode = process.waitFor();
            System.out.println("Python脚本输出：" + result);
            return result.toString();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static String Savepoints(String requestBody) {
        String exe = "C:\\Users\\86187\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        String py = "script3.py";
        String folder = "C:\\Users\\86187\\Desktop\\Screen-Shooting-Resilient-Watermarking-main";

        System.out.println("Received points:" + requestBody);

        try {
            // 构建进入指定文件夹的命令
            String[] cmdArr = new String[]{"cmd", "/c", "cd", folder, "&&", exe, py, "--corners", requestBody};
            ProcessBuilder processBuilder = new ProcessBuilder(cmdArr);

            // 将进程的输出流和错误流合并
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取进程的输出流
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // 等待进程执行结束
            int exitCode = process.waitFor();
            System.out.println("Python脚本输出：" + result);

            // 返回结果
            return result.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}