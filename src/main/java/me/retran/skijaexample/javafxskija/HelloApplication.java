package me.retran.skijaexample.javafxskija;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;
import org.jetbrains.skija.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

// lwjgl




// [1;2] [2;5] [3; 7] [6; 9] [10; 12]
// node - [3; 7] [2;5] [6;9]
// left - [1;2]
// right - [10; 12]

public class HelloApplication extends Application {
    private String text = "save hello world 123";
    private List<? extends Token> tokens;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        runLexer();
        render(scene);

        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                var c = keyEvent.getCharacter();
                if (c.equals("\r")) {
                    text += "\n";
                }
                else {
                    text += c;
                }

                runLexer();
                render(scene);
            }
        });

        stage.setTitle("Hello!");
        stage.setScene(scene);

        makeImageWithSkija();


        stage.show();
    }

    private void runLexer() {
        ourLangLexer lexer = new ourLangLexer(new ANTLRInputStream(text));
        tokens = lexer.getAllTokens().stream().toList();
    }

    private void render(Scene scene) {
        var canvas = (javafx.scene.canvas.Canvas) scene.getRoot().lookup("#myCanvas");
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 320, 240);
        var data = makeImageWithSkija().encodeToData().getBytes();
        javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(data));
        gc.drawImage(img, 0, 0);
    }

    private Image makeImageWithSkija() {
        Surface surface = Surface.makeRasterN32Premul(320, 240);
        Canvas canvas = surface.getCanvas();

        Paint paint = new Paint();
        paint.setColor(0xFFFF0000);

        int x = 30;
        int y = 50;

        var font = new Font(Typeface.makeFromName("Consolas", FontStyle.NORMAL));
        for (int i = 0; i < text.length(); i++) {
            Token currToken = null;
            for (var token : tokens) {
                if (token.getStartIndex() <= i && token.getStopIndex() >= i) {
                    currToken = token;
                    break;
                }
            }

            if (currToken != null) {
                switch (currToken.getType()) {
                    case 1:
                        paint.setColor(0xAABB0000);
                        break;
                    case 2:
                        paint.setColor(0xBBBBCC00);
                        break;
                    case 3:
                        paint.setColor(0x55551100);
                        break;
                    case 4:
                        paint.setColor(0x88683400);
                        break;
                }
            }

            char c = text.charAt(i);
            if (c != '\n') {
                var textLine = TextLine.make(String.valueOf(c), font);
                canvas.drawTextLine(textLine, x, y, paint);
                x += textLine.getWidth() + 1;
            }
            else {
                y += -font.getMetrics().getAscent() + font.getMetrics().getDescent() + font.getMetrics().getLeading();
                x = 30;
            }
        }


        return surface.makeImageSnapshot();
    }

    public static void main(String[] args) {
        launch();
    }
}