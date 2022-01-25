package com.zhunongyun.toalibaba.commoncode.utils;

import java.io.*;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * markdown字符替换
 * @author oscar
 */
public class FileHandler {

    private final static String FILE_PATH = "D:\\code\\idea_workspace\\common-code\\src\\main\\resources\\";

    private final static Pattern ENGLISH_HANDLER_PATTERN = Pattern.compile("[a-zA-z0-9\\(\\)]");

    public void file() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        URL url = Thread.currentThread().getContextClassLoader().getResource("old_file");

        try (FileReader reader = new FileReader(new File(url.getPath()));
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            boolean noHandler = false;
            while ((line = br.readLine()) != null) {
                // 处理替换字符
                if (!noHandler && line.contains("```")) {
                    noHandler = true;
                } else if (noHandler && line.contains("```")) {
                    noHandler = false;
                }

                if (!noHandler) {
                    line = line.replaceAll("：", ":")
                            .replaceAll("（", "(")
                            .replaceAll("）", ")")
                            .replaceAll("。", ".")
                            .replaceAll("，", ",")
                            .replaceAll("、", ",");
                    if (line.matches("^#+.*$")) {
                        if (line.matches("^#+ {1}[0-9]+\\.{1}.* {1}.*$")) {
                            int splitIndex = line.indexOf(" ", line.lastIndexOf(".")) + 1;

                            // 将英文单词/数字两边加上空格
                            String dataStr = this.handlerEnglish(line.substring(splitIndex));

                            line = line.substring(0, splitIndex) + dataStr;
                        }
                    } else if (line.contains(" | ")) {
                        String[] temp = line.split("\\|");

                        StringBuilder sb = new StringBuilder("|");
                        for (int i = 1; i < temp.length; i++) {
                            // 将英文单词/数字两边加上空格
                            String dataStr = this.handlerEnglish(temp[i]);
                            sb.append(" ").append(dataStr).append(" |");
                        }
                        line = sb.toString();
                    } else if (line.matches("^\\*{1} {1}.*$")) {
                        // 将英文单词/数字两边加上空格
                        String dataStr = this.handlerEnglish(line.substring(2));
                        line = line.substring(0, 2) + dataStr;
                    } else {
                        // 将英文单词/数字两边加上空格
                        line = this.handlerEnglish(line);
                    }
                    line = line.replace(">\\*", ">\\* ");
                }

                stringBuilder.append(line).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 写文件
        File outFile = new File(FILE_PATH + "new_file");
        outFile.createNewFile();

        try (FileWriter writer = new FileWriter(outFile);
             BufferedWriter out = new BufferedWriter(writer)) {

            out.write(stringBuilder.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String handlerEnglish(String line) {
        char[] charList = line.trim().toCharArray();

        if (charList.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < charList.length; i++) {
                sb.append(charList[i]);

                // 当前字符是本行最后一个,不用处理
                if (i == charList.length - 1) {
                    continue;
                }

                /**
                 * 如果当前字符为 字母 / () / 数字, 且当前字符不是本行最后一个
                 * 下一个字符有两种处理方式
                 * 如果是中文,则在中间添加一个空格
                 * 如果是 字母 / () / 数字 / 空格, 则不做任何处理
                 */
                if (this.checkEnglishAndNumber(charList[i], charList[i] == 41 || charList[i] == 93) && this.checkChinese(charList[i + 1])) {
                    sb.append(" ");
                    continue;
                }

                /**
                 * 如果当前字符为 中文
                 * 下一个字符有两种处理方式
                 * 如果是 字母 / () / 数字,则在中间添加一个空格
                 * 如果不是上面的情况, 则不做任何处理
                 */
                if (this.checkChinese(charList[i])) {
                    if (charList[i + 1] == 32) {
                        ++i;
                        if (charList[i + 1] == 32) {
                            ++i;
                        }
                    }

                    if (this.checkEnglishAndNumber(charList[i + 1], charList[i + 1] == 40 || charList[i + 1] == 91)) {
                        sb.append(" ");
                    }
                }
            }
            return sb.toString();
        }
        return line;
    }

    /**
     * 校验字符是否为 英文 / 数字 / %
     * @param ch 字符
     * @param extendBoolean 扩展判断, 用于 () 场景
     * @return true 字符为 英文 / 数字 / %  false 字符不是 英文 / 数字 / %
     */
    private boolean checkEnglishAndNumber(char ch, boolean extendBoolean) {
        return  (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch == 37 || extendBoolean;
    }

    /**
     * 校验字符是否为中文
     * @param ch 字符
     * @return true 字符为中文  false 字符不是中文
     */
    private boolean checkChinese(char ch) {
        return  ch >= 0x4e00 && ch <= 0x9fbb;
    }

    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler();
        fileHandler.file();
        System.out.println("执行成功");
    }
}