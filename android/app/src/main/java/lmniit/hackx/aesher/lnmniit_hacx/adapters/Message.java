package lmniit.hackx.aesher.lnmniit_hacx.adapters;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements IMessage {

    private String id;
    private String text;
    private Date createdAt;
    private Author author;


    public Message(String id, String text, Author author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return date;
    }
}
