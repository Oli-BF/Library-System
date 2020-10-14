import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseMethodsFriendTests {
    @Test
    public void test() {

        DatabaseMethodsFriend d= new DatabaseMethodsFriend();
        d.clearAll();

        DatabaseMethodsUser u= new DatabaseMethodsUser();
        u.register( "Member 4","password5","F4","L4","E4","T4","member" );

        d.addFriend("Member 1","Member 2" );
        d.addFriend( "Member 3","Member 2" );
        d.addFriend( "Member 4","Member 1" );

        d.addChat( "Member 2","Member 3","2 says HI 1 to 3" );
        d.addChat( "Member 3","Member 2","3 says HI 1 to 2" );
        d.addChat( "Member 2","Member 3","2 says HI 2 to 3" );
        d.addChat( "Member 3","Member 2","3 says HI 2 to 2" );

        d.addChat( "Member 1","Member 2","1 says HI 1 to 2" );
        d.addChat( "Member 1","Member 2","1 says HI 2 to 2" );
        d.addChat( "Member 1","Member 2","1 says HI 3 to 2" );
        d.addChat( "Member 2","Member 1","2 says HI 1 to 1" );
        d.addChat( "Member 2","Member 1","2 says HI 2 to 1" );
        d.addChat( "Member 1","Member 2","1 says HI 4 to 2" );
        d.addChat( "Member 2","Member 1","2 says HI 3 to 1" );


        d.addGlobalChat( "Member 3" ,"M3 is talking");
        d.addGlobalChat( "Member 1" ,"M1 is talking");
        d.addGlobalChat( "Member 2" ,"M2 is talking");

        ArrayList<String> chat1= new ArrayList<>(  );
        ArrayList<String> chat2= new ArrayList<>(  );
        chat1.add("2 says HI 3 to 1");chat1.add("1 says HI 4 to 2");chat1.add("2 says HI 2 to 1");
        chat1.add("2 says HI 1 to 1");chat1.add("1 says HI 3 to 2");chat1.add("1 says HI 2 to 2");
        chat1.add("1 says HI 1 to 2");

        chat2.add("M2 is talking");chat2.add("M1 is talking");chat2.add("M3 is talking");

        // Test getChatHistory, addChat
        ArrayList<ChatHistory> resutl1=d.getChatHistory("Member 1","Member 2");
        for(int i=0;i<chat1.size();++i) {
            assertEquals( chat1.get(i), resutl1.get(i).message,"some message does not match.");
        }

        // Test getGlobalChatHistory, addGlobalChat
        ArrayList<GlobalChatHistory> resutl2=d.getGlobalChatHistory();
        for(int i=0;i<chat2.size();++i) {
            assertEquals( chat2.get(i), resutl2.get(i).message,"some message does not match.");
        }

        // Test addFriend, getFriendListAndLogin
        HashSet<String> frineds= new HashSet<>(  );
        frineds.add("Member 2");frineds.add("Member 4");

        HashSet<String> results=new HashSet<>(  );
        results.addAll( d.getFriendListAndLogin( "Member 1").name);

        assertEquals( frineds,results,"friend list is wrong");
    }
}
