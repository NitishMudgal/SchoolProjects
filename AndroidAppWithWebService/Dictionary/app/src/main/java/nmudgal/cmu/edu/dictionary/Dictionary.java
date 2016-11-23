package nmudgal.cmu.edu.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nitish on 4/3/2016.
 */

public class Dictionary extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * The click listener will need a reference to this object, so that upon successfully finding dictionary xml respomnse, it
         * can callback to this object with the resulting string XML.  The "this" of the OnClick will be the OnClickListener
         */
        final Dictionary diction = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
                GetDictionary gDiction = new GetDictionary();
                gDiction.search(searchTerm, diction); // Done asynchronously in another thread.  It calls gDiction.synonymReady() in this thread when complete.
            }
        });
    }

    /*
     * This is called by the GetDictionary object when the picture is ready.
     * This allows for passing back the String XML and setting it in the resultView
     */

    public void synonymReady(String synonym) {
        EditText searchView = (EditText)findViewById(R.id.searchTerm);
        TextView resultView = (TextView)findViewById(R.id.resultView);
        if (!synonym.isEmpty()) {
            resultView.setText(synonym);
        } else {
            resultView.setText("Word not found, try another word");
        }
        searchView.setText("");
    }
}
