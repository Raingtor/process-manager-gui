package sample;

import lombok.Getter;
import lombok.Setter;
import sample.model.ProcessUser;

@Getter
@Setter
public class ProcessManager {
    private static ProcessUser currentUser;

    public static ProcessUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(ProcessUser currentUser) {
        ProcessManager.currentUser = currentUser;
    }
}
