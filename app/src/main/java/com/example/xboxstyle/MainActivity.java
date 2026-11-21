package com.example.xboxstyle;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Color;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(new Dashboard(this));
    }

    class Dashboard extends View {
        Paint p = new Paint(3);
        String[] tabs = {"INÍCIO", "MEUS JOGOS", "LOJA", "SOCIAL", "CONFIGURAÇÕES"};
        String[] games = {"Continuar jogando", "Biblioteca", "Explorar jogos", "Aplicativos"};
        int tab = 0, selected = 0;
        float downX, downY;
        Dashboard(android.content.Context c) { super(c); setFocusable(true); }

        void fill(Canvas c, int color, float l,float t,float r,float b,float radius) {
            p.setColor(color); p.setStyle(Paint.Style.FILL); c.drawRoundRect(new RectF(l,t,r,b),radius,radius,p);
        }
        void txt(Canvas c,String s,float x,float y,float size,int color,boolean bold) {
            p.setColor(color); p.setTextSize(size); p.setTypeface(bold?Typeface.create("sans-serif",Typeface.BOLD):Typeface.create("sans-serif",Typeface.NORMAL));
            c.drawText(s,x,y,p);
        }
        @Override protected void onDraw(Canvas c) {
            super.onDraw(c);
            float w=getWidth(), h=getHeight();
            c.drawColor(Color.rgb(15,19,16));
            int white=Color.WHITE, muted=Color.rgb(174,184,174), green=Color.rgb(128,217,40);
            txt(c,"X",28,58,34,green,true);
            txt(c,"XBOX STYLE",72,54,18,white,true);
            txt(c,"HOME EXPERIENCE",72,76,10,muted,false);
            txt(c,"●  Perfil     ◉  Notificações",w-270,52,13,white,false);
            float navY=105;
            for(int i=0;i<tabs.length;i++){
                float x=24+i*(w-48)/tabs.length;
                if(i==tab) fill(c,Color.rgb(44,66,35),x,navY,x+(w-58)/tabs.length,navY+42,12);
                txt(c,tabs[i],x+12,navY+27, i==tab?12:11, i==tab?green:muted,true);
            }
            txt(c, tab==0?"VOLTAR AO JOGO":tabs[tab],28,205,25,white,true);
            txt(c,"Seus jogos e entretenimento em um só lugar",28,232,13,muted,false);
            if(tab==0 || tab==1) {
                String[] labels = tab==0 ? games : new String[]{"Todos os jogos","Instalados","Favoritos","Adicionar jogo"};
                float gap=14, left=28, top=260, cardW=(w-56-gap*3)/4, cardH=Math.min(170,h*.27f);
                int[] colors={Color.rgb(44,93,37),Color.rgb(31,74,89),Color.rgb(87,55,104),Color.rgb(75,77,71)};
                for(int i=0;i<4;i++){
                    float x=left+i*(cardW+gap);
                    fill(c,colors[i],x,top,x+cardW,top+cardH,16);
                    txt(c, i==0?"▶":i==1?"▦":i==2?"✦":"＋",x+18,top+45,30,white,true);
                    txt(c,labels[i],x+16,top+cardH-22,13,white,true);
                    if(i==selected) {
                        p.setColor(green); p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(3);
                        c.drawRoundRect(new RectF(x,top,x+cardW,top+cardH),16,16,p); p.setStyle(Paint.Style.FILL);
                    }
                }
                txt(c,"DICA",28,top+cardH+48,11,green,true);
                txt(c,"Use as abas para navegar. Toque em um cartão para selecionar.",28,top+cardH+72,13,muted,false);
            } else {
                String msg = tab==2 ? "Loja demonstrativa — adicione seus links de jogos favoritos."
                    : tab==3 ? "Área social demonstrativa — perfil e atalhos."
                    : "Personalize o visual e escolha este app como tela inicial.";
                fill(c,Color.rgb(29,37,30),28,260,w-28,430,18);
                txt(c,msg,52,315,15,white,false);
                txt(c,"Esta é uma interface independente, não conectada aos serviços Xbox.",52,350,12,muted,false);
            }
            txt(c,"Aperte voltar para sair  •  Toque nas abas para navegar",28,h-24,11,muted,false);
        }
        @Override public boolean onTouchEvent(MotionEvent e) {
            if(e.getAction()==MotionEvent.ACTION_DOWN){downX=e.getX();downY=e.getY();return true;}
            if(e.getAction()==MotionEvent.ACTION_UP){
                float x=e.getX(), y=e.getY(), w=getWidth();
                if(y>=100 && y<=155){ tab=Math.min(tabs.length-1,Math.max(0,(int)((x-24)/((w-48)/tabs.length)))); selected=0; invalidate(); return true; }
                if(y>=250 && y<480){ selected=(int)((x-28)/((w-56)/4)); selected=Math.max(0,Math.min(3,selected)); invalidate(); return true; }
                invalidate(); return true;
            }
            return true;
        }
    }
}
