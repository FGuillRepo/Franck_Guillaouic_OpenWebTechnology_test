package com.guillaouic.test;

import com.guillaouic.test.pojo.Book;
import com.guillaouic.test.pojo.Item;
import com.guillaouic.test.repository.DataRepository;
import com.guillaouic.test.viewmodel.BookViewModel;

import org.junit.Before;		
import org.junit.Rule;		
import org.junit.Test;		
import org.junit.runner.RunWith;		
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

import android.arch.core.executor.testing.InstantTaskExecutorRule;		
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
		
import static org.hamcrest.CoreMatchers.is;		
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;		
import static org.mockito.Mockito.reset;		
import static org.mockito.Mockito.verify;		
import static org.mockito.Mockito.verifyNoMoreInteractions;		
import static org.mockito.Mockito.when;		

@RunWith(JUnit4.class)
public class ViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();
    private Application application;
    private Book book;

    @Mock
    private BookViewModel viewModel;
    private DataRepository repository;

    @Before
    public void init() {
        repository = mock(DataRepository.class);
        application = Mockito.mock(Application.class);
        viewModel = new BookViewModel();
    }

    @Test
    public void empty() {
        Observer<Book> result = mock(Observer.class);
        viewModel.getBooks().observeForever(result);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void basic() {
        book = generateItem();

        Observer<Book> observer = mock(Observer.class);
        viewModel.getBooks().observeForever(observer);
        viewModel.getRepository().getBook().setValue(book);
        viewModel.getRepository().loadNetworkData().getBooks_Network("value");
        verify(observer).onChanged(book);
      //  assertEquals("test", viewModel.getBooks());
        Mockito.verifyNoMoreInteractions(observer);
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
