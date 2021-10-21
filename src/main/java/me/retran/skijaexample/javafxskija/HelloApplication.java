package me.retran.skijaexample.javafxskija;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.skija.*;

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

interface IVisitor {

    void visit(LoadStatement loadStatement);
    void visit(SaveStatement saveStatement);
    void visit(Statements statements);

    void visit(ErrorStatement errorStatement);
}

abstract class VisitorBase implements IVisitor {
    @Override
    public void visit(Statements statements) {
        beforeVisit(statements);
        for (var statement : statements.getStatements()) {
            statement.accept(this);
        }
        afterVisit(statements);
    }

    @Override
    public void visit(LoadStatement loadStatement) {
        beforeVisit(loadStatement);
        afterVisit(loadStatement);
    }

    @Override
    public void visit(SaveStatement saveStatement) {
        beforeVisit(saveStatement);
        afterVisit(saveStatement);
    }

    @Override
    public void visit(ErrorStatement errorStatement) {
        beforeVisit(errorStatement);
        afterVisit(errorStatement);
    }

    public abstract void afterVisit(ErrorStatement errorStatement);
    public abstract void beforeVisit(ErrorStatement errorStatement);

    public abstract void beforeVisit(Statements statements);
    public abstract void afterVisit(Statements statements);

    public abstract void beforeVisit(LoadStatement statements);
    public abstract void afterVisit(LoadStatement statements);

    public abstract void beforeVisit(SaveStatement statements);
    public abstract void afterVisit(SaveStatement statements);
}

class OurAnalyzer extends VisitorBase {
    private List<ErrorHighlighter> highlighters;
    private List<LoadStatement> loadStatements = new ArrayList<>();

    public OurAnalyzer(List<ErrorHighlighter> highlighters) {

        this.highlighters = highlighters;
    }

    @Override
    public void afterVisit(ErrorStatement errorStatement) {
        highlighters.add(new ErrorHighlighter(errorStatement.getStart(), errorStatement.getEnd()));
    }

    @Override
    public void beforeVisit(ErrorStatement errorStatement) {

    }

    @Override
    public void beforeVisit(Statements statements) {

    }

    @Override
    public void afterVisit(Statements statements) {
        for (var loadStatement : loadStatements) {
            highlighters.add(new ErrorHighlighter(loadStatement.getStart(), loadStatement.getEnd()));}

    }

    @Override
    public void beforeVisit(LoadStatement statement) {
        loadStatements.add(statement);
    }

    @Override
    public void afterVisit(LoadStatement statements) {

    }

    @Override
    public void beforeVisit(SaveStatement statements) {

    }

    @Override
    public void afterVisit(SaveStatement saveStatement) {
        var loadStatement = loadStatements.stream().filter(s -> s.getValue().equals(saveStatement.getValue())).findFirst();
        if (loadStatement.isPresent()) {
            loadStatements.remove(loadStatement.get());
        }
        else
        {
            highlighters.add(new ErrorHighlighter(saveStatement.getStart(), saveStatement.getEnd()));
        }
    }
}

abstract class AstNode {
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

    public abstract void accept(IVisitor visitor);
}

abstract class Statement extends AstNode {
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

class ErrorStatement extends Statement {
    protected ErrorStatement() {
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}


class LoadStatement extends Statement {
    protected LoadStatement() {
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}

class SaveStatement extends  Statement {
    protected SaveStatement() {
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}

class Statements extends AstNode {
    private final List<Statement> statements;

    Statements() {
        this.statements = new ArrayList<>();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public List<Statement> getStatements() {
        return statements;
    }
}

public class HelloApplication extends Application {
    private String text = "LOAD qwe; qwer; SAVE qwe;";
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


        Screen screen = Screen.getPrimary();
        double scaleX = screen.getOutputScaleX();
        double scaleY = screen.getOutputScaleY();
        makeImageWithSkija(scaleX, scaleY);


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

        var analyser = new OurAnalyzer(highlighters);
        this.root.accept(analyser);
    }

    private void render(Scene scene) {
        var canvas = (javafx.scene.canvas.Canvas) scene.getRoot().lookup("#myCanvas");
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 320, 240);

        Screen screen = Screen.getPrimary();
        double scaleX = screen.getOutputScaleX();
        double scaleY = screen.getOutputScaleY();

        var data = makeImageWithSkija(scaleX, scaleY).encodeToData().getBytes();
        javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(data));
        gc.drawImage(img, 0, 0, 320, 240);
    }

    private Image makeImageWithSkija(double scaleX, double scaleY) {
        Surface surface = Surface.makeRasterN32Premul((int)(320 * scaleX), (int)(240 * scaleY));
        Canvas canvas = surface.getCanvas();

        Paint paint = new Paint();
        paint.setColor(0xFFFF0000);

        int x = 30;
        int y = 50;

        var font = new Font(Typeface.makeFromName("Consolas", FontStyle.NORMAL), (float) (14 * scaleY));
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