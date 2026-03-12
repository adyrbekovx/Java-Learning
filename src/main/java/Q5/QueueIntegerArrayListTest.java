package Q5;

import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueueIntegerArrayListTest {

    @org.testng.annotations.Test
    public void testFifoAndDynamicResizing() {
        // Создаем очередь с маленькой вместимостью
        Q5.Test queue = new Q5.Test(2);

        assertEquals(0, queue.getSize(), "Начальный размер должен быть 0");

        // Добавляем 3 элемента (на 3-м должно сработать расширение массива)
        queue.add(10);
        queue.add(20);
        queue.add(30);

        assertEquals(3, queue.getSize(), "Размер после добавления должен быть 3");
        assertEquals(10, queue.peek(), "Первым элементом должен быть 10");

        // Проверяем порядок извлечения (FIFO) и сдвиг элементов
        assertEquals(10, queue.remove(), "Ожидается 10");
        assertEquals(20, queue.remove(), "Ожидается 20");
        assertEquals(1, queue.getSize(), "Размер должен уменьшиться до 1");

        assertEquals(30, queue.remove(), "Ожидается 30");
        assertEquals(0, queue.getSize(), "Очередь должна стать пустой");
    }

    @org.testng.annotations.Test
    public void testEmptyQueueExceptions() {
        Q5.Test queue = new Q5.Test(5);

        // Проверяем, что методы бросают ошибку, если очередь пуста
        assertThrows(NoSuchElementException.class, queue::remove,
                "remove() должен бросать исключение при пустой очереди");

        assertThrows(NoSuchElementException.class, queue::peek,
                "peek() должен бросать исключение при пустой очереди");
    }
}