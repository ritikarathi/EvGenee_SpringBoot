package com.voltx.evgenee.ai;

public class ToolResultHolder {

    private static final ThreadLocal<ToolResult> RESULT = new ThreadLocal<>();

    public static void set(ToolResult result) {
        RESULT.set(result);
    }

    public static ToolResult get() {
        return RESULT.get();
    }

    public static void clear() {
        RESULT.remove();
    }

    public record ToolResult(String bookingId, Boolean redirect, Object stations) {}
}
