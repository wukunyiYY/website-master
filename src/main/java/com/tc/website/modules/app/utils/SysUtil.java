package com.tc.website.modules.app.utils;

import com.tc.website.common.utils.AesUtil;
import com.tc.website.common.utils.ByteUtil;
import com.tc.website.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 *  许可授权专用
 */
public class SysUtil {

    private static final Logger logger = LoggerFactory.getLogger(SysUtil.class);

    private static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }


    public static String getCPUSerial() {
        if (getOSName().startsWith("windows")) {
            return getCPUSerial_windows();
        } else {
            return getCPUSerial_linux();
        }
    }

    public static String getDiskSerial() {
        if (getOSName().startsWith("windows")) {
            return getDiskSerial_windows("c");
        } else {
            return getDiskSerial_linux();
        }
    }

    public static String getMotherboardSerial() {
        if (getOSName().startsWith("windows")) {
            return getMotherboardSerial_windows();
        } else {
            return getMotherboardSerial_linux();
        }
    }

    /**
     * 获取cpu系列号
     * @return
     */
    public static String getCPUSerial_windows() {
        // 默认值
        String serial = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                serial += line;
            }
            input.close();
            file.delete();
            if(StringUtils.isNotBlank(serial)){
                serial += "_";
            }
        } catch (Exception e) {
            logger.error("CPUSerial", e);
        }
        // 操作系统信息
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        if(StringUtils.isNotBlank(osArch) && StringUtils.isNotBlank(osVersion)){
            serial += osArch + "_" + osVersion;
        }
        logger.debug("CPUSerial_windows = " + serial);
        System.out.println("CPUSerial_windows = " + serial);
        byte[] serialEnc = AesUtil.encrypt(serial, AesUtil.PASSWORD);
        String serialEncStr = ByteUtil.byte2Hex(serialEnc).toUpperCase();
        return serialEncStr;
    }

    /**
     *  获取cpu系列号
     * @return
     */
    public static String getCPUSerial_linux(){
        String serial = "";
        String CPU_ID_CMD = "dmidecode -t 1";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{ "sh", "-c", CPU_ID_CMD });// 管道
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("uuid");
                if (index >= 0) {
                    serial = line.substring(index + "uuid".length() + 1).trim();
                    break;
                }
            }
            if(StringUtils.isNotBlank(serial)){
                serial += "_";
            }
        } catch (IOException e) {
            logger.error("CPUSerial", e);
        }
        // 操作系统信息
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        if(StringUtils.isNotBlank(osArch) && StringUtils.isNotBlank(osVersion)){
            serial += osArch + "_" + osVersion;
        }
        logger.debug("CPUSerial = " + serial);
        System.out.println("CPUSerial = " + serial);
        byte[] serialEnc = AesUtil.encrypt(serial, AesUtil.PASSWORD);
        String serialEncStr = ByteUtil.byte2Hex(serialEnc).toUpperCase();
        return serialEncStr;
    }

    /**
     * 获取硬盘序列号
     * @param drive 磁盘驱动号：默认c
     * @return
     */
    public static String getDiskSerial_windows(String drive) {
        // 默认值
        String serial = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                serial += line;
            }
            input.close();
            if(StringUtils.isNotBlank(serial)){
                serial += "_";
            }
        } catch (Exception e) {
            logger.error("DiskSerial", e);
        }
        // 操作系统信息
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        if(StringUtils.isNotBlank(osArch) && StringUtils.isNotBlank(osVersion)){
            serial += osArch + "_" + osVersion;
        }
        logger.debug("DiskSerial_windows = " + serial);
        System.out.println("DiskSerial_windows = " + serial);
        byte[] serialEnc = AesUtil.encrypt(serial.trim(), AesUtil.PASSWORD);
        String serialEncStr = ByteUtil.byte2Hex(serialEnc).toUpperCase();
        return serialEncStr;
    }


    /**
     * 获取硬盘序列号
     * @return
     */
    public static String getDiskSerial_linux(){
        // 默认值
        String serial = "";
        // 操作系统信息
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        if(StringUtils.isNotBlank(osArch) && StringUtils.isNotBlank(osVersion)){
            serial += osArch + "_" + osVersion;
        }
        logger.debug("DiskSerial = " + serial);
        System.out.println("DiskSerial = " + serial);
        byte[] serialEnc = AesUtil.encrypt(serial.trim(), AesUtil.PASSWORD);
        String serialEncStr = ByteUtil.byte2Hex(serialEnc).toUpperCase();
        return serialEncStr;
    }

    /**
     * 获取主板系列号
     * @return
     */
    public static String getMotherboardSerial_windows() {
        String serial = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                serial += line;
            }
            input.close();
            if(StringUtils.isNotBlank(serial)){
                serial += "_";
            }
        } catch (Exception e) {
            logger.error("MotherboardSerial", e);
        }
        // 操作系统信息
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        if(StringUtils.isNotBlank(osArch) && StringUtils.isNotBlank(osVersion)){
            serial += osArch + "_" + osVersion;
        }
        logger.debug("MotherboardSerial_windows = " + serial);
        System.out.println("MotherboardSerial_windows = " + serial);
        byte[] serialEnc = AesUtil.encrypt(serial, AesUtil.PASSWORD);
        String serialEncStr = ByteUtil.byte2Hex(serialEnc).toUpperCase();
        return serialEncStr;
    }

    /**
     * 获取主板系列号、系统系列号
     * @return
     */
    public static String getMotherboardSerial_linux() {
        String serial = "";
        String maniBord_cmd = "dmidecode -s system-serial-number";
        Process p;
        try {
            p = Runtime.getRuntime().exec(
                    new String[] { "sh", "-c", maniBord_cmd });// 管道
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                serial += line + "_";
                break;
            }
            br.close();
            if(StringUtils.isNotBlank(serial)){
                serial += "_";
            }
        } catch (IOException e) {
            logger.error("MotherboardSerial", e);
        }
        // 操作系统信息
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        if(StringUtils.isNotBlank(osArch) && StringUtils.isNotBlank(osVersion)){
            serial += osArch + "_" + osVersion;
        }
        logger.debug("MotherboardSerial = " + serial);
        System.out.println("MotherboardSerial = " + serial);
        byte[] serialEnc = AesUtil.encrypt(serial, AesUtil.PASSWORD);
        String serialEncStr = ByteUtil.byte2Hex(serialEnc).toUpperCase();
        return serialEncStr;
    }

    public static void main(String[] args) {
        System.out.println("cpu = " + getCPUSerial());
        System.out.println("disk = "+ getDiskSerial());
        System.out.println("motherboard = " + getMotherboardSerial());
    }
}
