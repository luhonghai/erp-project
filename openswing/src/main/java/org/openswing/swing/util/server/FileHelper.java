package org.openswing.swing.util.server;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Created by cmg on 20/05/2016.
 */
public class FileHelper {

    public static Class BASE_CLASS = FileHelper.class;

    public static File getTmpWar() {
        File erpDir;
        try {
            erpDir = new File(new File(getWebRootResourceUri()).getParentFile(), "data");
        } catch (Exception e) {
            e.printStackTrace();
            erpDir = new File(FileUtils.getTempDirectory(), "data");
        }
        if (!erpDir.exists()) {
            erpDir.mkdirs();
        }
        return erpDir;
    }

    public static String getRootResource() {
        try {
            File jar = new File(getWebRootResourceUri());
            String path =  jar.getAbsolutePath();
            if (jar.isFile()) {
                path = getTmpWar().getAbsolutePath();
            }
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            return path;
        } catch (Exception e) {
            return new File(".").getAbsolutePath();
        }

    }

    public static URI getWebRootResourceUri() throws FileNotFoundException, URISyntaxException
    {
        ProtectionDomain domain = BASE_CLASS.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        String path = location.toURI().toASCIIString();
        if (path.startsWith("jar:") && path.contains("!")) {
            path = path.substring(4, path.lastIndexOf("!"));
            return new URI(path);
        }
        return location.toURI();
    }
}
