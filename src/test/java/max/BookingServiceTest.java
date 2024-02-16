package max;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookingServiceTest {
    private static final Logger logger
            = LoggerFactory.getLogger(BookingService.class);

    @Test
    @DisplayName("Проверка брони с любыми параметрами.")
    void getBookedWithCorrectTime() throws CantBookException {
        logger.info("Тест код запущен");
        //Given
        logger.debug("Формирование мока для BookingService ");
        BookingService bookingServiceMock = mock(BookingService.class);
        //When
        Mockito.when(bookingServiceMock.book(
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class))).thenReturn(true);
        //Then
        logger.debug("Проверка результатов");
        Assertions.assertEquals(true, bookingServiceMock
                .book("John Snow", LocalDateTime.now(), LocalDateTime.now()));
        logger.debug("Тест окончен");
    }

    //Проверка брони, с методами вызова конкретного метода мок обьекта book с проверками на:
    //1 - n или больше раз
    //2 - 1 или больше раз
    //3 - Должен быть только один вызов и только к этому методу

    @Test
    @DisplayName("Проверка брони с любыми параметрами 2")
    void positiveBookingTest() throws CantBookException {
        logger.info("Тест код запущен");
        //Given
        logger.debug("Формирование мока для BookingService ");
        BookingService bookingServiceMock = mock(BookingService.class);
        //When
        Mockito.when(bookingServiceMock.book(
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class))).thenReturn(true);
        //Then
        logger.debug("Проверка результатов");
        Assertions.assertEquals(true, bookingServiceMock
                .book("John Snow",
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        //1
        Mockito.verify(bookingServiceMock, Mockito.atLeast(1)).book(anyString()
                , any(LocalDateTime.class)
                , any(LocalDateTime.class));
        //2
        Mockito.verify(bookingServiceMock, Mockito.atLeastOnce()).book(anyString()
                , any(LocalDateTime.class)
                , any(LocalDateTime.class));
        //3
        Mockito.verify(bookingServiceMock, Mockito.only()).book(anyString()
                , any(LocalDateTime.class)
                , any(LocalDateTime.class));
        logger.info("Тест positiveBookingTest прошёл успешно");

    }

    // //Проверка брони, с методами вызова конкретного метода мок обьекта book с проверками на:
    //1 - количество вызовов 1
    //2 - никаких других обращений к объекту bookingServiceMock не было за время теста.
    @Test
    @DisplayName("Проверка брони с любыми параметрами 3")
    void negativeBookingTest() throws CantBookException {
        logger.info("Тест код запущен");
        //Given
        logger.debug("Формирование мока для BookingService ");
        BookingService bookingServiceMock = mock(BookingService.class);
        //When
        Mockito.doThrow(CantBookException.class)
                .when(bookingServiceMock)
                .book(anyString()
                        , any(LocalDateTime.class)
                        , any(LocalDateTime.class));
        //Then
        Assertions.assertThrows(CantBookException.class, () -> bookingServiceMock.book("John Snow",
                LocalDateTime.now(),
                LocalDateTime.now()));
      //1
        Mockito.verify(bookingServiceMock, Mockito.atLeast(1)).book(anyString()
                , any(LocalDateTime.class)
                , any(LocalDateTime.class));
      //2
        Mockito.verifyNoMoreInteractions(bookingServiceMock);
    }
}