package in.aqel.smartsilencer;

import android.databinding.ObservableField;

/**
 * Created by Ahammad on 14/12/16.
 */

public class User {
    public ObservableField<String> firstName = new ObservableField<>();
    public String lastName;
    public User(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName = lastName;
    }
}