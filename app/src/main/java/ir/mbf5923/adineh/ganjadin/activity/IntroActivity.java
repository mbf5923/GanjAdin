package ir.mbf5923.adineh.ganjadin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import ir.mbf5923.adineh.ganjadin.intropage.pageone;
import ir.mbf5923.adineh.ganjadin.intropage.pagethree;
import ir.mbf5923.adineh.ganjadin.intropage.pagetwo;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntroActivity  extends MaterialIntroActivity {
    private static final int NUM_PAGES = 4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.colorPrimary)
//                        .buttonsColor(R.color.colorAccent)
//                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
//                        .neededPermissions(new String[]{Manifest.permission.CAMERA})
//                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
//                        .title("title 3")
//                        .description("Description 3")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(IntroActivity.this, "We provide solutions to make you love your work", Toast.LENGTH_SHORT).show();
//                    }
//                }, "Work with love"));

        addSlide(new pageone());
        addSlide(new pagetwo());
        addSlide(new pagethree());
//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.colorPrimary)
//                        .buttonsColor(R.color.colorAccent)
//                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
//                        .neededPermissions(new String[]{Manifest.permission.CAMERA})
//                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
//                        .title("هر جمعه شانس میلیونر شدنت رو امتحان کن...")
//                        .description("توی مسابقه شرکت کن و بدون قرعه کشی جایزه برنده شو")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(IntroActivity.this, "We provide solutions to make you love your work", Toast.LENGTH_SHORT).show();
//                    }
//                }, "Work with love"));
//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.colorPrimary)
//                        .buttonsColor(R.color.colorAccent)
//                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
//                        .neededPermissions(new String[]{Manifest.permission.CAMERA})
//                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
//                        .title("title 3")
//                        .description("Description 3")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(IntroActivity.this, "We provide solutions to make you love your work", Toast.LENGTH_SHORT).show();
//                    }
//                }, "Work with love"));



    }

    @Override
    public void onFinish() {
        super.onFinish();
        SHPManager.getInstance().SetShowIntro(1);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
