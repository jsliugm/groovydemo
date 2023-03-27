package com.universe.demo.executor;

import com.sun.management.HotSpotDiagnosticMXBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JFRDemo {

    public static void main(String[] args) throws Exception {

        // Get the PID of the local JVM process
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        System.out.println("pid:"+pid);
        // Start the JFR recording using jcmd
        Path recordingFile = Paths.get("/tmp/recording.jfr");
        Files.deleteIfExists(recordingFile);

        ProcessBuilder pb1 = new ProcessBuilder(
                "jcmd", pid, "JFR.start",
                "name=MyRecording", "filename=/tmp/recording.jfr",
                "duration=120s", "maxsize=1024m");

        ProcessBuilder pb = new ProcessBuilder(
                "jcmd", pid, "JFR.start",
                "name=MyRecording", "filename=/tmp/recording.jfr",
                "duration=120s", "maxsize=1024m");
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Failed to start JFR recording: " + exitCode);
            System.exit(1);
        }

        // Wait for the recording to finish
        Thread.sleep(2000);

        // Dump the recording to a file
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName diagnosticMXBeanName = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
        HotSpotDiagnosticMXBean diagnosticMXBean = ManagementFactory.newPlatformMXBeanProxy(server, diagnosticMXBeanName.getCanonicalName(), HotSpotDiagnosticMXBean.class);

        diagnosticMXBean.dumpHeap(recordingFile.toString(), true);
    }
}
