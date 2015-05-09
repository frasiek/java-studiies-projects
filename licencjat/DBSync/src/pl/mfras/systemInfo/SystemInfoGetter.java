/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mfras.systemInfo;

import java.io.File;

/**
 *
 * @author frasiek
 */
public class SystemInfoGetter {

    public String GetInfo() {
        String info = "";
        info += System.getProperty("os.name")+" ";
        info += System.getProperty("os.arch")+"\r\n";
        info += System.getProperty("user.name")+"\r\n";
        info += "Available processors (cores): " + Runtime.getRuntime().availableProcessors() + "\r\n";
        info += "Free memory (bytes): " + humanReadableByteCount(Runtime.getRuntime().freeMemory(), true) + "\r\n";
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        for (File root : roots) {
            info += "\r\n"+"File system root: " + root.getAbsolutePath() + "\r\n";
            info += "Total space (bytes): " + humanReadableByteCount(root.getTotalSpace(), true) + "\r\n";
            info += "Free space (bytes): " + humanReadableByteCount(root.getFreeSpace(), true) + "\r\n";
            info += "Usable space (bytes): " + humanReadableByteCount(root.getUsableSpace(), true)+ "\r\n";
        }
        return info;
    }

    public String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

}
