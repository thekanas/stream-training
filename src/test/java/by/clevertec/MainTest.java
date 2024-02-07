package by.clevertec;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class MainTest {
    protected ByteArrayOutputStream output;
    private PrintStream old;

    @BeforeEach
    public void setUpStreams() {
        old = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(old);
    }

    @Test
    void task15Test() {
        Main.task15();
        String expected = "Общая стоимость и расходы на обслуживание за 5 лет выбранных растений составят: 368110,85";
        Assertions.assertEquals(expected, output.toString());
    }
}