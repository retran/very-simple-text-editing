package me.retran.skijaexample.javafxskija;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.skija.*;
import org.jetbrains.skija.shaper.ShapingOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class ErrorHighlighter {
    private int start;
    private int end;

    public ErrorHighlighter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

class AstNode {
    private int start;
    private int end;

    protected AstNode() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

class Statement extends AstNode {
    private String value;

    protected Statement() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class LoadStatement extends Statement {
    protected LoadStatement() {
    }
}

class SaveStatement extends  Statement {
    protected SaveStatement() {
    }
}

class Statements extends AstNode {
    private final List<Statement> statements;

    Statements() {
        this.statements = new ArrayList<>();
    }

    public List<Statement> getStatements() {
        return statements;
    }
}

public class HelloApplication extends Application {
    private String text = "LOAD qwe; SAVE qwe;";
    private List<? extends Token> tokens;
    private List<ErrorHighlighter> highlighters;
    private Statements root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        runLexer();
        render(scene);

        scene.setOnKeyTyped(keyEvent -> {
            var c = keyEvent.getCharacter();
            if (c.equals("\r")) {
                text += "\n";
            }
            else {
                text += c;
            }

            runLexer();
            render(scene);
        });

        stage.setTitle("Hello!");
        stage.setScene(scene);

        makeImageWithSkija();


        stage.show();
    }

    private void runLexer() {
        highlighters = new ArrayList<>();

        me.retran.skijaexample.javafxskija.ourLangLexer lexer =
                new me.retran.skijaexample.javafxskija.ourLangLexer(new ANTLRInputStream(text));

        Stack<AstNode> nodes = new Stack<>();

        final Statements[] root = {null};

        var parser = new me.retran.skijaexample.javafxskija.ourLangParser(new CommonTokenStream(lexer));
        parser.addParseListener(new me.retran.skijaexample.javafxskija.ourLangListener() {
            @Override
            public void enterLoadStatement(me.retran.skijaexample.javafxskija.ourLangParser.LoadStatementContext ctx) {
                var node = new LoadStatement();
                ((Statements) nodes.peek()).getStatements().add(node);
                nodes.push(node);
            }

            @Override
            public void exitLoadStatement(me.retran.skijaexample.javafxskija.ourLangParser.LoadStatementContext ctx) {
                if (nodes.peek() instanceof LoadStatement) {
                    var node = (LoadStatement) nodes.pop();
                    node.setStart(ctx.start.getStartIndex());
                    node.setEnd(ctx.getStop().getStopIndex());

                    if (ctx.LETTERS() != null) {
                        node.setValue(ctx.LETTERS().getText());
                    } else {
                        highlighters.add(new ErrorHighlighter(node.getStart(), node.getEnd()));
                    }
                }
            }

            @Override
            public void enterSaveStatement(me.retran.skijaexample.javafxskija.ourLangParser.SaveStatementContext ctx) {
                var node = new SaveStatement();
                ((Statements) nodes.peek()).getStatements().add(node);
                nodes.push(node);
            }

            @Override
            public void exitSaveStatement(me.retran.skijaexample.javafxskija.ourLangParser.SaveStatementContext ctx) {
                if (nodes.peek() instanceof SaveStatement) {
                    var node = (SaveStatement) nodes.pop();
                    node.setStart(ctx.start.getStartIndex());
                    node.setEnd(ctx.getStop().getStopIndex());

                    if (ctx.LETTERS() != null) {
                        node.setValue(ctx.LETTERS().getText());
                    } else {
                        highlighters.add(new ErrorHighlighter(node.getStart(), node.getEnd()));
                    }
                }
            }

            @Override
            public void enterStatements(me.retran.skijaexample.javafxskija.ourLangParser.StatementsContext ctx) {
                var node = new Statements();
                if (root[0] == null) {
                    root[0] = node;
                }
                nodes.push(node);
            }

            @Override
            public void exitStatements(me.retran.skijaexample.javafxskija.ourLangParser.StatementsContext ctx) {
                if (nodes.peek() instanceof Statements) {
                    var node = (Statements) nodes.pop();
                    node.setStart(ctx.start.getStartIndex());
                    node.setEnd(ctx.getStop().getStopIndex());
                }
            }

            @Override
            public void visitTerminal(TerminalNode terminalNode) {

            }

            @Override
            public void visitErrorNode(ErrorNode errorNode) {

            }

            @Override
            public void enterEveryRule(ParserRuleContext parserRuleContext) {

            }

            @Override
            public void exitEveryRule(ParserRuleContext parserRuleContext) {

            }
        });


        parser.statements();

        this.root = root[0];

        lexer.reset();
        
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

            boolean highlight = false;
            for (var highlighter : highlighters) {
                if (highlighter.getStart() <= i && highlighter.getEnd() >= i) {
                    highlight = true;
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
                if (highlight) {
                    var hy = y + 2;
                    paint.setColor(0xAABB0000);
                    canvas.drawLine(x, hy, x + textLine.getWidth() + 1, hy, paint);
                }
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