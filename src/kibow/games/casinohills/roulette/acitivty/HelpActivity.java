package kibow.games.casinohills.roulette.acitivty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class HelpActivity extends Activity implements OnClickListener {

    Bundle b;
    Button btnTutorial, btnAboutGame, btnAboutBitcoin;
    ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        b = new Bundle();
        btnTutorial = (Button) findViewById(R.id.btn_tutorial);
        btnAboutGame = (Button) findViewById(R.id.btn_about_game);
        btnAboutBitcoin = (Button) findViewById(R.id.btn_about_bitcoin);
        ;
        imgBack = (ImageButton) findViewById(R.id.btn_back);
        imgBack.setOnClickListener(this);
        btnTutorial.setOnClickListener(this);
        btnAboutBitcoin.setOnClickListener(this);
        btnAboutGame.setOnClickListener(this);

    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.btn_tutorial:
                Intent a = new Intent(HelpActivity.this, WebviewHelpPage.class);
                a.putExtra("idButton", 0);
                startActivity(a);
                break;
            case R.id.btn_about_bitcoin:
                Intent b = new Intent(HelpActivity.this, WebviewHelpPage.class);
                b.putExtra("idButton", 2);
                startActivity(b);
                break;
            case R.id.btn_about_game:
                Intent c = new Intent(HelpActivity.this, WebviewHelpPage.class);
                c.putExtra("idButton", 1);
                startActivity(c);
                break;
            case R.id.btn_back:
                this.finish();
                break;
        }


    }


}
