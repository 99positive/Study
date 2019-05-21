package atomatic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class UseAtomicReference {
    static AtomicReference<User> atomicReference;

    public static void main(String[] args) {
        User userA = new User("self", 18);
        User userB = new User("other", 15);
        atomicReference = new AtomicReference<>(userA) ;

        atomicReference.compareAndSet(userA, userB);

        System.out.println(atomicReference.get());
        System.out.println(userA);
        System.out.println(userB);
    }

    static class User{
        private String userName;

        private Integer age;

        public User(String userName, Integer age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
