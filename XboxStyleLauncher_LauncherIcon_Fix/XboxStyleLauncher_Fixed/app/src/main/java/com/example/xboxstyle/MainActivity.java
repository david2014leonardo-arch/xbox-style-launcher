package com.example.xboxstyle;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.MotionEvent;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(5894 | 1024 | 512);
        setContentView(new Dashboard());
    }

    class Dashboard extends View {
        Paint p = new Paint(3);
        String[] tabs = {"INÍCIO", "MEUS JOGOS", "LOJA", "SOCIAL", "CONFIGURAÇÕES"};
        int selected = 0;
        Dashboard() { super(MainActivity.this); setFocusable(true); }
        void text(Canvas c, String s, float x, float y, float size, int color, boolean bold) {
            p.setColor(color); p.setTextSize(size); p.setTypeface(bold ? Typeface.create("sans-serif", Typeface.BOLD) : Typeface.create("sans-serif", Typeface.NORMAL)); c.drawText(s,x,y,p);
        }
        void rect(Canvas c,float l,float t,float r,float b,int color,float radius) { p.setColor(color); c.drawRoundRect(l,t,r,b,radius,radius,p); }
        @Override protected void onDraw(Canvas c) {
            super.onDraw(c); float w=getWidth(), h=getHeight();
            c.drawColor(Color.rgb(9,13,11));
            text(c,"X",28,55,30,Color.rgb(16,124,16),true);
            text(c,"XBOX",67,52,18,Color.WHITE,true);
            text(c,"STYLE LAUNCHER",67,70,9,0xff9da89f,false);
            float tabY=115;
            for(int i=0;i<tabs.length;i++) { float x=24+i*(w-48)/tabs.length; if(i==selected) rect(c,x,tabY-25,x+(w-48)/tabs.length-8,tabY+8,0xff164d22,12); text(c,tabs[i],x+8,tabY,11,i==selected?Color.WHITE:0xffaab4ac,true); }
            text(c, selected==0?"Bem-vindo de volta":" "+tabs[selected],24,185,25,Color.WHITE,true);
            text(c,"SEUS JOGOS E ENTRETENIMENTO EM UM SÓ LUGAR",24,211,10,0xff9da89f,false);
            String[] games={"HALO", "FORZA", "MINECRAFT", "ROCKET LEAGUE"};
            int[] colors={0xff254b35,0xff25455a,0xff66502b,0xff49365e};
            float gap=12, left=24, cardW=(w-48-gap*3)/4, top=245;
            for(int i=0;i<4;i++) { float x=left+i*(cardW+gap); rect(c,x,top,x+cardW,top+cardW*0.85f,colors[i],14); text(c,games[i],x+10,top+cardW*0.48f,Math.max(10,cardW*0.11f),Color.WHITE,true); }
            rect(c,24,h-95,w-24,h-35,0xff151d18,14);
            text(c,"DICA",40,h-69,11,0xff58c45b,true);
            text(c,"Adicione seus jogos favoritos e personalize sua experiência.",40,h-49,11,0xffd5ddd6,false);
            text(c,"Interface demonstrativa • escolha este app como tela inicial nas configurações do Android",24,h-12,8,0xff7d887f,false);
        }
        @Override public boolean onTouchEvent(MotionEvent e) {
            if(e.getAction()==MotionEvent.ACTION_UP) { float w=getWidth(); if(e.getY()>75 && e.getY()<145) { selected=Math.max(0,Math.min(tabs.length-1,(int)((e.getX()-24)/((w-48)/tabs.length)))); invalidate(); return true; } }
            return true;
        }
    }
}
