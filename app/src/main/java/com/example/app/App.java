package com.example.app;

import com.example.core.CoreUtil;
import org.apache.commons.lang3.StringUtils;

public class App {
    public static void main(String[] args) {
        System.out.println(CoreUtil.getCoreMessage());
        System.out.println(StringUtils.reverse("Another message from App!"));
    }
}
