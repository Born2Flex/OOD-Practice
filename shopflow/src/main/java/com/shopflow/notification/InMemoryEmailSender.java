package com.shopflow.notification;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEmailSender implements EmailSender {

    private final List<EmailMessage> sent = new ArrayList<>();

    @Override
    public void send(EmailMessage message) {
        sent.add(message);
    }

    public List<EmailMessage> sent() {
        return List.copyOf(sent);
    }
}
