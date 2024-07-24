package com.sun.kvem.cldc.i18n;

import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;

public class GB2312MID extends MIDlet implements CommandListener {

    Form form;

    Command cmdQuit;

    HGB2312 gb;

    public GB2312MID() {
        form = new Form("GB2312");
        cmdQuit = new Command("quit", Command.EXIT, 0);

        form.addCommand(cmdQuit);
        form.setCommandListener(this);

        Display.getDisplay(this).setCurrent(form);

        test();
    }

    void test() {

        try {
            gb = new HGB2312();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String t = "GB2312编码，真是烦";
        byte[] d_gb2312 = null;
        byte[] d_unicode = null;
        byte[] d_utf8 = null;
        try {
            d_gb2312 = t.getBytes("GB2312");
            form.append("GB2312 YES");
        } catch (Exception e) {
            form.append("GB2312 NO");
        }
        try {
            d_unicode = t.getBytes("UTF-16BE");
            form.append("UTF-16BE YES");
        } catch (Exception e) {
            form.append("UTF-16BE NO");
        }

        try {
            d_utf8 = t.getBytes("UTF-8");
            form.append("UTF-8 YES");
        } catch (Exception e) {
            form.append("UTF-8 NO");
        }

        try {
            form.append(" ");
            form.append(gb.gb2utf8(d_gb2312));
            gb.gb2utf8(d_gb2312).getBytes();
        } catch (Exception e) {
            form.append(e.toString());
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (d.equals(form)) {
            if (c.equals(cmdQuit)) {
                notifyDestroyed();
            }
        }
    }

    protected void destroyApp(boolean arg0) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }

    public class HGB2312 {

        private byte[] map = new byte[15228];

        public HGB2312() throws Exception {
            InputStream is = getClass().getResourceAsStream("/gb2u.dat");
            is.read(map);
            is.close();
        }

        public String gb2utf8(byte[] gb) {
            StringBuffer sb = new StringBuffer();
            int c, h, l, ind;
            for (int i = 0; i < gb.length;) {
                if (gb[i] >= 0) {
                    sb.append((char) gb[i++]);
                } else {
                    h = 256 + gb[i++];
                    l = 256 + gb[i++];
                    h = h - 0xA0 - 1;
                    l = l - 0xA0 - 1;
                    if (h < 9) {
                        ind = (h * 94 + l) << 1;
                        c = (byte2Int(map[ind]) << 8 | byte2Int(map[ind + 1]));
                        sb.append((char) c);
                    } else if (h >= 9 && h <= 14) {
                        sb.append((char) 0);
                    } else if (h > 14) {
                        h -= 6;
                        ind = (h * 94 + l) << 1;
                        c = (byte2Int(map[ind]) << 8 | byte2Int(map[ind + 1]));
                        sb.append((char) c);

                    } else {
                        sb.append((char) 0);
                    }
                }
            }
            return sb.toString();
        }

        private int byte2Int(byte b) {
            if (b < 0) {
                return 256 + b;
            } else {
                return b;
            }
        }
    }
}
