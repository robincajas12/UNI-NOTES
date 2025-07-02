package com.patrones;

import com.patrones.impl.Chat;
import com.patrones.impl.ConcreteUser;
import com.patrones.inter.User;

public class Main  {
    public static void main(String[] args) {
        Chat miChat = new Chat();
        User robin = new ConcreteUser(miChat, "robin");
        User juan = new ConcreteUser(miChat, "juan");
        miChat.addUser(robin);
        miChat.addUser(juan);
        miChat.sendMessage("Hola...", juan);
        miChat.sendMessage("Hola juan...", robin);
        juan.send("Holaaaaaaaa a todos");
        robin.send("Holaaa mundoioooooooooooooooooooooo");

    }
}
