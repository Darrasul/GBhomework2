package ru.buzas.client.model;

import ru.buzas.clientserver.Command;

public interface ReadCommandListener {

    void processReceivedMessage(Command command);
}
