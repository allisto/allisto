package lmniit.hackx.aesher.lnmniit_hacx.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import lmniit.hackx.aesher.lnmniit_hacx.R;
import lmniit.hackx.aesher.lnmniit_hacx.adapters.Author;
import lmniit.hackx.aesher.lnmniit_hacx.adapters.Message;


public class Chatbot extends Fragment implements MessagesListAdapter.OnLoadMoreListener {

    String TAG = "Chatbot";

    @BindView(R.id.question)
    EditText question;

    @BindView(R.id.send)
    ImageButton sendButton;

    Author author;
    Message message;
    MessagesListAdapter<Message> adapter;

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chatbot, container , false);

        ButterKnife.bind(this,v);

        final ai.api.android.AIConfiguration config = new ai.api.android.AIConfiguration("e0d78e0c11bc4c519a0961f81f23576b",
                AIConfiguration.SupportedLanguages.English,
                ai.api.android.AIConfiguration.RecognitionEngine.System);


        final AIDataService aiDataService = new AIDataService(config);
        final AIRequest aiRequest = new AIRequest();


        List<Message> randomShit;
        randomShit = new ArrayList<>();
        author = new Author("1", "Aashis", "hey");
        message = new Message("1", "Welcome. How can i help you", author);
        randomShit.add(message);
        adapter = new MessagesListAdapter<>("2", null);
        messagesList.setAdapter(adapter);
        adapter.addToEnd(randomShit, true);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG,question.getText().toString());
                author = new Author("2", "chatbot", "hey");
                message = new Message("2", question.getText().toString(), author);
                adapter.addToStart(message, true);
                aiRequest.setQuery(question.getText().toString());
                new AsyncTask<AIRequest, Void, AIResponse>() {
                    @Override
                    protected AIResponse doInBackground(AIRequest... requests) {
                        try {
                            return aiDataService.request(aiRequest);
                        } catch (AIServiceException ignored) {
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(AIResponse aiResponse) {

                        question.setText("");
                        if(aiResponse!=null){
                            Log.w(TAG,""+aiResponse);
                            author = new Author("1", "Aashis", "hey");
                            message = new Message("1", aiResponse.getResult().getFulfillment().getSpeech(), author);
                            adapter.addToStart(message, true);
                        }



                    }
                }.execute(aiRequest);
            }
        });





        return v;

    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {

    }
}
