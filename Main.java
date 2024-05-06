public class Main {
    public static void main(String[] args) {
        displayWelcomeMessage();
        Game game = new Game();
        game.startGame();
    }

    private static void displayWelcomeMessage() {
        String messageArt = "██     ██ ███████ ██       ██████  ██████  ███    ███ ███████     ████████  ██████           ██  █████  ██    ██  █████  ███████ ██ ███████ ██      ██████  \n"
                +
                "██     ██ ██      ██      ██      ██    ██ ████  ████ ██             ██    ██    ██          ██ ██   ██ ██    ██ ██   ██ ██      ██ ██      ██      ██   ██ \n"
                +
                "██  █  ██ █████   ██      ██      ██    ██ ██ ████ ██ █████          ██    ██    ██          ██ ███████ ██    ██ ███████ █████   ██ █████   ██      ██   ██ \n"
                +
                "██ ███ ██ ██      ██      ██      ██    ██ ██  ██  ██ ██             ██    ██    ██     ██   ██ ██   ██  ██  ██  ██   ██ ██      ██ ██      ██      ██   ██ \n"
                +
                " ███ ███  ███████ ███████  ██████  ██████  ██      ██ ███████        ██     ██████       █████  ██   ██   ████   ██   ██ ██      ██ ███████ ███████ ██████  \n";

        System.out.println();
        System.out.println(messageArt);
    }
}