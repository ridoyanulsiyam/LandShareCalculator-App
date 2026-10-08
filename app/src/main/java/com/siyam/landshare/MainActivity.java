package com.siyam.landshare;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.*;

import java.util.Locale;

public class MainActivity extends Activity {
    static final int BG = Color.rgb(250,251,253);
    static final int TEXT = Color.rgb(24,31,42);
    static final int MUTED = Color.rgb(100,110,125);
    static final int BORDER = Color.rgb(225,229,236);
    static final int ORANGE = Color.rgb(241,128,43);
    static final int BLUE = Color.rgb(39,112,235);
    static final int RED = Color.rgb(220,72,72);

    Typeface kalpurush;
    LinearLayout root;
    TextView ana, gonda, kora, kranti, til;
    EditText totalInput;
    TextView result;

    final String[] anaSymbols = {"⁄","৵","৶","৷","৷⁄","৷৵","৷৶","৷৷","৷৷⁄","৷৷৵","৷৷৶","৸","৸⁄","৸৵","৸৶","১"};
    final String[] anaNames = {"১ আনা","২ আনা","৩ আনা","৪ আনা","৫ আনা","৬ আনা","৭ আনা","৮ আনা","৯ আনা","১০ আনা","১১ আনা","১২ আনা","১৩ আনা","১৪ আনা","১৫ আনা","১৬ আনা"};
    final String[] koraSymbols = {"৷","৷৷","৸"};
    final String[] krantiSymbols = {"৴","৴৴"};

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        kalpurush = Typeface.createFromAsset(getAssets(), "fonts/Kalpurush.ttf");
        Window w = getWindow();
        if (android.os.Build.VERSION.SDK_INT >= 30) w.setDecorFitsSystemWindows(false);
        showHome();
    }

    int dp(float n) { return (int)(n * getResources().getDisplayMetrics().density + .5f); }
    TextView tv(String s, float size) { TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(TEXT); t.setTypeface(kalpurush); return t; }
    TextView english(String s, float size) { TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(MUTED); t.setTypeface(Typeface.DEFAULT); return t; }

    GradientDrawable rounded(int[] colors, int radius, int strokeColor) {
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        g.setCornerRadius(dp(radius));
        if (strokeColor != 0) g.setStroke(dp(1), strokeColor);
        return g;
    }
    GradientDrawable solid(int color, int radius) { return rounded(new int[]{color,color}, radius, 0); }

    void base(LinearLayout content) {
        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(Color.rgb(248,249,251));
        root.setPadding(dp(20), dp(14), dp(20), dp(16)); root.addView(content, new LinearLayout.LayoutParams(-1,-1));
        setContentView(root);
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            root.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets wi = insets;
                android.graphics.Insets i = wi.getInsets(android.view.WindowInsets.Type.systemBars());
                v.setPadding(dp(20), dp(12)+i.top, dp(20), dp(14)+i.bottom);
                return insets;
            });
            root.requestApplyInsets();
        } else {
            root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    TextView header(String title, String subtitle) {
        LinearLayout box = new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setGravity(Gravity.CENTER_VERTICAL);
        TextView back = tv("‹", 30); back.setGravity(Gravity.CENTER); back.setTextColor(TEXT); back.setOnClickListener(v -> showHome());
        box.addView(back, new LinearLayout.LayoutParams(dp(44),dp(40)));
        TextView h = tv(title, 22); h.setTypeface(kalpurush, Typeface.BOLD); box.addView(h, new LinearLayout.LayoutParams(-1,dp(34)));
        TextView sub = tv(subtitle, 13); sub.setTextColor(MUTED); box.addView(sub, new LinearLayout.LayoutParams(-1,dp(28)));
        return box;
    }

    TextView homeTitle() { TextView h=tv("জমির হিসাব",24); h.setTypeface(kalpurush,Typeface.BOLD); h.setTextColor(TEXT); return h; }

    Button menuButton(String text, int[] colors) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(16);
        b.setTextColor(Color.WHITE);
        b.setTypeface(kalpurush,Typeface.BOLD);
        b.setGravity(Gravity.CENTER);
        b.setAllCaps(false);
        b.setMinHeight(0);
        b.setMinimumHeight(0);
        b.setPadding(dp(14),0,dp(14),0);
        b.setBackground(rounded(colors,20,Color.argb(75,255,255,255)));
        b.setElevation(dp(2));
        return b;
    }

    void showHome() {
        LinearLayout c = new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL); c.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView h = homeTitle(); c.addView(h, new LinearLayout.LayoutParams(-1,dp(48)));
        Space top = new Space(this); c.addView(top,new LinearLayout.LayoutParams(1,dp(18)));

        Button info = menuButton("আনা, গন্ডা, কড়া, ক্রান্তি ও তিলের সাংকেতিক চিহ্ন", new int[]{Color.rgb(250,155,67),ORANGE});
        c.addView(info,new LinearLayout.LayoutParams(-1,dp(82))); info.setOnClickListener(v->showSymbols());
        Space s1=new Space(this); c.addView(s1,new LinearLayout.LayoutParams(1,dp(14)));
        Button calc=menuButton("খতিয়ানের হিসাব",new int[]{Color.rgb(82,147,250),BLUE});
        c.addView(calc,new LinearLayout.LayoutParams(-1,dp(70))); calc.setOnClickListener(v->showCalculator());
        Space s2=new Space(this); c.addView(s2,new LinearLayout.LayoutParams(1,dp(14)));
        Button warning=menuButton("* ব্যবহারিক নীতিমালা *",new int[]{Color.rgb(238,103,103),RED});
        c.addView(warning,new LinearLayout.LayoutParams(-1,dp(54))); warning.setOnClickListener(v->showDisclaimer());

        Space flex = new Space(this); c.addView(flex,new LinearLayout.LayoutParams(1,0,1));
        LinearLayout credit = new LinearLayout(this); credit.setOrientation(LinearLayout.VERTICAL); credit.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView d=english("Developed by Md. Ridoyanul Hoq Siyam",11); d.setGravity(Gravity.CENTER); d.setAlpha(.68f); credit.addView(d,new LinearLayout.LayoutParams(-1,dp(20)));
        TextView p=english("Phone: +8801780103463",11); p.setGravity(Gravity.CENTER); p.setAlpha(.68f); credit.addView(p,new LinearLayout.LayoutParams(-1,dp(20)));
        c.addView(credit,new LinearLayout.LayoutParams(-1,dp(42)));
        base(c);
    }

    void showSymbols() {
        LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL);
        TextView head=(TextView)header("সাংকেতিক চিহ্ন", "আনা, গণ্ডা, কড়া, ক্রান্তি ও তিলের ধারাবাহিক বিবরণ"); c.addView(head,new LinearLayout.LayoutParams(-1,dp(102)));
        LinearLayout list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); list.setPadding(0,dp(3),0,0);
        addInfo(list,"১. আনা","১৬ আনা = ১ পূর্ণ অংশ", "১–১৬ আনার সাংকেতিক চিহ্ন:  ⁄  ৵  ৶  ৷  ৷⁄  ৷৵  ৷৶  ৷৷  ৷৷⁄  ৷৷৵  ৷৷৶  ৸  ৸⁄  ৸৵  ৸৶  ১");
        addInfo(list,"২. গণ্ডা","২০ গণ্ডা = ১ আনা", "সিলেকশনে ১–১৯ গণ্ডা");
        addInfo(list,"৩. কড়া","৪ কড়া = ১ গণ্ডা", "সিলেকশনে ১–৩ কড়া; চিহ্ন: ৷, ৷৷, ৸");
        addInfo(list,"৪. ক্রান্তি","৩ ক্রান্তি = ১ কড়া", "সিলেকশনে ১–২ ক্রান্তি; চিহ্ন: ৴, ৴৴");
        addInfo(list,"৫. তিল","২০ তিল = ১ ক্রান্তি", "সিলেকশনে ১–১৯ তিল");
        c.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        base(c);
    }

    void addInfo(LinearLayout list,String title,String relation,String detail){
        LinearLayout card=new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL); card.setPadding(dp(12),dp(8),dp(12),dp(8)); card.setBackground(rounded(new int[]{Color.WHITE,Color.rgb(247,249,252)},14,BORDER));
        TextView a=tv(title,16); a.setTypeface(kalpurush,Typeface.BOLD); card.addView(a,new LinearLayout.LayoutParams(-1,dp(25)));
        TextView b=tv(relation,13); b.setTextColor(BLUE); card.addView(b,new LinearLayout.LayoutParams(-1,dp(22)));
        TextView d=tv(detail,12); d.setTextColor(MUTED); card.addView(d,new LinearLayout.LayoutParams(-1,dp(32)));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,0,1); p.setMargins(0,dp(3),0,dp(3)); list.addView(card,p);
    }

    void showCalculator() {
        LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL);
        TextView head=(TextView)header("খতিয়ানের হিসাব", "সঠিকভাবে অংশ নির্বাচন করে ফলাফল দেখুন"); c.addView(head,new LinearLayout.LayoutParams(-1,dp(102)));
        LinearLayout total=new LinearLayout(this); total.setOrientation(LinearLayout.VERTICAL); total.setPadding(dp(12),dp(7),dp(12),dp(7)); total.setBackground(rounded(new int[]{Color.WHITE,Color.rgb(248,250,253)},14,BORDER));
        total.addView(tv("মোট জমি (শতাংশ)",13),new LinearLayout.LayoutParams(-1,dp(24)));
        totalInput=new EditText(this); totalInput.setHint("০"); totalInput.setTextSize(17); totalInput.setTypeface(kalpurush); totalInput.setSingleLine(true); totalInput.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL); totalInput.setPadding(dp(10),0,dp(10),0); totalInput.setBackground(solid(Color.WHITE,10)); total.addView(totalInput,new LinearLayout.LayoutParams(-1,dp(44)));
        c.addView(total,new LinearLayout.LayoutParams(-1,dp(78)));
        LinearLayout grid=new LinearLayout(this); grid.setOrientation(LinearLayout.VERTICAL); String[] names={"আনা","গণ্ডা","কড়া","ক্রান্তি","তিল"};
        for(int i=0;i<5;i++) addCalcRow(grid,names[i],i);
        LinearLayout.LayoutParams gp=new LinearLayout.LayoutParams(-1,0,1); gp.topMargin=dp(7); c.addView(grid,gp);
        Button calc=new Button(this); calc.setText("হিসাব করুন"); calc.setTextSize(15); calc.setTypeface(kalpurush,Typeface.BOLD); calc.setTextColor(Color.WHITE); calc.setAllCaps(false); calc.setBackground(rounded(new int[]{Color.rgb(74,142,246),BLUE},14,0)); calc.setOnClickListener(v->calculate()); c.addView(calc,new LinearLayout.LayoutParams(-1,dp(48)));
        result=tv("ফলাফল এখানে দেখাবে",13); result.setTextColor(MUTED); result.setGravity(Gravity.CENTER_VERTICAL); c.addView(result,new LinearLayout.LayoutParams(-1,dp(48)));
        base(c);
    }

    void addCalcRow(LinearLayout grid,String name,int index){
        LinearLayout row=new LinearLayout(this); row.setGravity(Gravity.CENTER_VERTICAL); TextView l=tv(name,14); l.setTypeface(kalpurush,Typeface.BOLD); row.addView(l,new LinearLayout.LayoutParams(dp(70),-1));
        TextView value=tv("০",17); value.setGravity(Gravity.CENTER_VERTICAL); value.setPadding(dp(12),0,dp(12),0); value.setBackground(rounded(new int[]{Color.WHITE,Color.rgb(248,250,253)},10,BORDER)); row.addView(value,new LinearLayout.LayoutParams(0,-1,1));
        row.setPadding(0,dp(2),0,dp(2)); grid.addView(row,new LinearLayout.LayoutParams(-1,0,1));
        if(index==0)ana=value; else if(index==1)gonda=value; else if(index==2)kora=value; else if(index==3)kranti=value; else til=value;
        value.setOnClickListener(v->showPicker(name,value,index));
    }

    void showPicker(String unit,TextView target,int index){
        String[] symbols,names;
        if(index==0){symbols=anaSymbols;names=anaNames;}
        else if(index==1){symbols=nums(19);names=names(19,unit);}
        else if(index==2){symbols=koraSymbols;names=names(3,unit);}
        else if(index==3){symbols=krantiSymbols;names=names(2,unit);}
        else {symbols=nums(19);names=names(19,unit);}
        LinearLayout list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); list.setPadding(dp(8),dp(4),dp(8),dp(4));
        ScrollView sv=new ScrollView(this); sv.addView(list); AlertDialog dlg=new AlertDialog.Builder(this).setTitle(unit+" নির্বাচন করুন").setView(sv).create();
        for(int i=0;i<symbols.length;i++){ final String s=symbols[i], n=names[i]; TextView opt=tv(s+"     "+n,16); opt.setGravity(Gravity.CENTER_VERTICAL); opt.setPadding(dp(16),0,dp(12),0); opt.setBackground(solid(Color.WHITE,10)); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,dp(48)); p.setMargins(0,dp(3),0,dp(3)); list.addView(opt,p); opt.setOnClickListener(v->{target.setText(s);dlg.dismiss();}); }
        dlg.show();
    }
    String[] nums(int n){String[] a=new String[n];for(int i=0;i<n;i++)a[i]=bn(i+1);return a;}
    String[] names(int n,String u){String[] a=new String[n];for(int i=0;i<n;i++)a[i]=bn(i+1)+" "+u;return a;}
    String bn(int v){StringBuilder b=new StringBuilder();for(char ch:String.valueOf(v).toCharArray())b.append((char)('\u09E6'+(ch-'0')));return b.toString();}
    int indexOf(String[] a,String x){for(int i=0;i<a.length;i++)if(a[i].equals(x))return i;return -1;}
    int anaIndex(String s){return indexOf(anaSymbols,s)+1;}
    int parseInt(String s){try{return Integer.parseInt(toLatin(s).replaceAll("[^0-9]",""));}catch(Exception e){return 0;}}
    String toLatin(String s){StringBuilder b=new StringBuilder();for(char ch:s.toCharArray())b.append(ch>='\u09E6'&&ch<='\u09EF'?(char)('0'+(ch-'\u09E6')):ch);return b.toString();}
    double parse(String s){try{return Double.parseDouble(toLatin(s).replace(',','.').trim());}catch(Exception e){return 0;}}

    void calculate(){
        double total=parse(totalInput.getText().toString());
        if(total<=0){totalInput.setError("মোট জমির পরিমাণ লিখুন");return;}
        int a=Math.max(0,anaIndex(ana.getText().toString())); int g=parseInt(gonda.getText().toString()); int k=indexOf(koraSymbols,kora.getText().toString())+1; int c=indexOf(krantiSymbols,kranti.getText().toString())+1; int t=parseInt(til.getText().toString());
        long shareTil=((long)a*20*4*3*20)+((long)g*4*3*20)+((long)k*3*20)+((long)c*20)+t;
        if(shareTil<=0){result.setText("অংশ নির্বাচন করুন");return;}
        if(shareTil>76800){result.setText("মোট অংশ ১৬ আনার বেশি হতে পারে না");return;}
        double fraction=shareTil/76800.0; double area=total*fraction; double pct=fraction*100.0;
        result.setText("শতাংশ: "+bnDecimal(area)+"  |  অংশ: "+bnDecimal(pct)+"%");
    }
    String bnDecimal(double x){String s=String.format(Locale.US,"%.4f",x).replaceAll("0+$","").replaceAll("\\.$","");StringBuilder b=new StringBuilder();for(char ch:s.toCharArray()){if(ch>='0'&&ch<='9')b.append((char)('\u09E6'+(ch-'0')));else if(ch=='.')b.append('.');else b.append(ch);}return b.toString();}

    void showDisclaimer(){
        LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL);
        TextView head=(TextView)header("ব্যবহারিক নীতিমালা", "ব্যবহারের আগে নিচের নির্দেশনাগুলো পড়ুন"); c.addView(head,new LinearLayout.LayoutParams(-1,dp(102)));
        ScrollView sv=new ScrollView(this); LinearLayout list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL);
        String[] items={
                "১. এই অ্যাপ ব্যবহারের আগে আনা, গণ্ডা, কড়া, ক্রান্তি ও তিলের সাংকেতিক চিহ্নগুলো ভালোভাবে পড়ে ও বুঝে নিন।",
                "২. হিসাব করার সময় প্রতিটি ইনপুট সঠিকভাবে প্রদান করুন। ভুল বা অসম্পূর্ণ ইনপুট দিলে ফলাফলও ভুল হতে পারে। এ ধরনের ভুল ফলাফলের জন্য ডেভেলপার দায়ী থাকবেন না।",
                "৩. এই অ্যাপটি শুধুমাত্র শিক্ষামূলক ও শেখার উদ্দেশ্যে তৈরি করা হয়েছে।",
                "৪. জমি সংক্রান্ত গুরুত্বপূর্ণ সিদ্ধান্ত নেওয়ার আগে ভূমি বিশেষজ্ঞ, আইনজীবী বা সংশ্লিষ্ট সরকারি কর্তৃপক্ষের পরামর্শ ও যাচাই গ্রহণ করুন।",
                "৫. এই অ্যাপ কোনো সরকারি, আইনগত বা পেশাদার ভূমি-সেবা ব্যবস্থা নয়। এর ফলাফল সরকারি নথি বা আইনগত প্রমাণ হিসেবে ব্যবহার করা যাবে না।",
                "৬. কোনো তথ্য, হিসাব, সাংকেতিক চিহ্ন বা পদ্ধতিতে ভুল বা অসংগতি লক্ষ্য করলে ডেভেলপারের সঙ্গে যোগাযোগ করুন।",
                "৭. এটি একজন ডিজাইনারের ব্যক্তিগত উদ্যোগ ও আগ্রহ থেকে তৈরি একটি শিক্ষামূলক প্রকল্প। এটি কোনো সরকারি প্রতিষ্ঠান বা ভূমি অফিসের সঙ্গে সম্পৃক্ত নয়।",
                "৮. অ্যাপের তথ্য ও হিসাবের যথার্থতা নিশ্চিত করার চেষ্টা করা হলেও সম্পূর্ণ নির্ভুলতার নিশ্চয়তা প্রদান করা হচ্ছে না।",
                "৯. এই অ্যাপের ফলাফলের ভিত্তিতে নেওয়া কোনো সিদ্ধান্ত, লেনদেন, চুক্তি বা কার্যক্রমের জন্য ডেভেলপার দায়ী থাকবেন না।",
                "১০. অ্যাপ ব্যবহার করে কোনো ব্যক্তি কোনো অবৈধ বা অপরাধমূলক কর্মকাণ্ডে জড়িত হলে তার দায়ভার সংশ্লিষ্ট ব্যবহারকারীর। এ ধরনের কর্মকাণ্ডের জন্য ডেভেলপার দায়ী থাকবেন না।",
                "১১. কোনো হিসাব বা তথ্য নিয়ে সন্দেহ থাকলে যোগ্য ব্যক্তি বা সংশ্লিষ্ট কর্তৃপক্ষের মাধ্যমে যাচাই করুন।",
                "১২. এই অ্যাপে কোনো বিজ্ঞাপন প্রদর্শন করা হয় না এবং অ্যাপ ব্যবহারের জন্য কোনো অ্যাকাউন্ট বা লগইন প্রয়োজন হয় না।",
                "১৩. হিসাবের ইনপুট ও ফলাফল কোনো অনলাইন সার্ভারে পাঠানো হয় না এবং হিসাব ডিভাইসেই সম্পন্ন হয়।",
                "১৪. অ্যাপটি কোনো সরকারি ভূমি-তথ্য সিস্টেম বা তৃতীয় পক্ষের ডাটাবেসের সঙ্গে সংযুক্ত নয়।"
        };
        for(String s:items){TextView t=tv(s,13);t.setTextColor(Color.rgb(55,65,81));t.setPadding(dp(12),dp(7),dp(12),dp(7));t.setBackground(rounded(new int[]{Color.WHITE,Color.rgb(249,250,252)},12,BORDER));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,dp(3),0,dp(3));list.addView(t,p);}
        TextView credit=english("Developed by Md. Ridoyanul Hoq Siyam\nPhone: +8801780103463",11);credit.setGravity(Gravity.END);credit.setAlpha(.7f);credit.setPadding(0,dp(8),0,0);list.addView(credit,new LinearLayout.LayoutParams(-1,dp(48)));
        sv.addView(list);c.addView(sv,new LinearLayout.LayoutParams(-1,0,1));base(c);
    }
}
