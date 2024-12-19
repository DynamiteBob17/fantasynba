package io.input;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String takeInput(String message, String defaultValue) {
        if (defaultValue != null) message += " [default=%s]: ".formatted(defaultValue);

        System.out.print(message);
        String input = scanner.nextLine();

        return input.isBlank() ? defaultValue : input;
    }

}
