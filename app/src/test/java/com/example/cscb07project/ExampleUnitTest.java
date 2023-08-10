package com.example.cscb07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.example.cscb07project.ui.login.activity_login_contract;
import com.example.cscb07project.ui.login.activity_login_presenter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    activity_login_contract.View view;
    @Mock
    activity_login_contract.Model model;

    @Test
    public void checkDoLoginEmailEmptyEmail() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.doLoginEmail("", "password");
        verify(view).doToast("Enter your Email");
        verify(view).progressBarVisibility(4);
    }

    @Test
    public void checkDoLoginEmailEmptyPassword() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.doLoginEmail("bestEmail@hmail.com", "");
        verify(view).doToast("Enter your Password");
        verify(view).progressBarVisibility(4);
    }

    @Test
    public void checkDoLoginEmailNormal() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.doLoginEmail("e", "p");
        verify(model).loggingInUser("e", "p");
    }

    @Test
    public void checkDoToast() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.doToast("I luv toast");
        verify(view).doToast("I luv toast");
    }

    @Test
    public void checkDoCheckLoggedIn() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.doCheckLoggedIn();
        verify(model).checkLoggedIn();
    }

    @Test
    public void checkIsLoggedIn() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.isLoggedIn();
        verify(view).loggedIn();
    }

    @Test
    public void checkChangeProgressBarVisibility() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view, model);
        presenter.changeProgressBarVisibility(0);
        verify(view).progressBarVisibility(0);
    }

    @Test
    public void checkConstructor() {
        activity_login_contract.Presenter presenter = new activity_login_presenter(view);
        assertEquals(presenter, new activity_login_presenter(view));
    }
}