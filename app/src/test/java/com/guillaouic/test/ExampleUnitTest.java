package com.guillaouic.test;

import android.arch.lifecycle.Observer;

import com.guillaouic.test.pojo.bookModel.Book;
import com.guillaouic.test.pojo.bookModel.Item;
import com.guillaouic.test.viewmodel.BookViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Book> observer;
    private Application application;


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Before
    public void setup() {
    }

    @Test
    public void test_clickReturnCorrectObject() {
        Book book = generateItem();
        BookViewModel viewModel = new BookViewModel();

        viewModel.getBookList().observeForever(observer);
        // When
        viewModel.getBookList().postValue(book);
        // then
        verify(observer).onChanged(book);
    }

    private Book generateItem() {
        Book book = new Book();
        book.setKind("test");

        List<Item> itemlist = new ArrayList<>();
        Item item = new Item();
        item.setId("Book id 1");
        book.setItems(itemlist);
        return book;
    }
}