package com.shopflow.notification;

public record EmailMessage(String to, String subject, String body) {
}
