package com.example.cscb07project.ui.login;

public class activity_login_presenter implements activity_login_contract.Presenter {

    activity_login_contract.View view;
    activity_login_contract.Model model;

    public activity_login_presenter(activity_login_contract.View view) {
        this.view = view;
        model = new activity_login_model(this);
    }

    public activity_login_presenter(activity_login_contract.View view, activity_login_contract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void doLoginEmail(String email, String password) {
        // check if email or password is empty
        if(email.isEmpty()) {
            doToast("Enter your Email");
            view.progressBarVisibility(4);
            return;
        }
        if(password.isEmpty()) {
            doToast("Enter your Password");
            view.progressBarVisibility(4);
            return;
        }
        model.loggingInUser(email, password);
    }

    @Override
    public void doToast(String message) {
        view.doToast(message);
    }

    @Override
    public void doCheckLoggedIn() {
        model.checkLoggedIn();
    }

    @Override
    public void isLoggedIn() {
        view.loggedIn();
    }

    @Override
    public void changeProgressBarVisibility(int mode) {
        view.progressBarVisibility(mode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        activity_login_presenter that = (activity_login_presenter) o;
        return view.equals(that.view);
    }

}
