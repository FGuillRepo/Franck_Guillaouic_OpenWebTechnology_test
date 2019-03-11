package com.guillaouic.test;

import android.arch.lifecycle.Observer;

import com.guillaouic.test.pojo.Book;
import com.guillaouic.test.pojo.Item;
import com.guillaouic.test.viewmodel.BookViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    private Book book;
    private BookViewModel viewModel;

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
        application = Mockito.mock(Application.class);

    }

    @Test
    public void test_ReturnBookOject() {

        //Test return object of viewmodel
        book = generateItem();
        viewModel = new BookViewModel();
        viewModel.getBooks().observeForever(observer);
        viewModel.mSearchClickCallback.onClick("test");
        viewModel.getRepository().getBook().postValue(book);
        verify(observer).onChanged(book);
    }

    private Book generateItem() {
        Book book = new Book();
        book.setKind("test");
        book.setTotalItems(1);

        List<Item> itemlist = new ArrayList<>();
        Item item = new Item();
        item.setId("Book id 1");
        itemlist.add(item);
        book.setItems(itemlist);
        return book;
    }
}