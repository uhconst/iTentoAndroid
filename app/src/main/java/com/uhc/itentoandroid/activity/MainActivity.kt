package com.uhc.itentoandroid.activity

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import com.uhc.itentoandroid.App
import com.uhc.itentoandroid.R
import com.uhc.itentoandroid.util.PrefsKeys

class MainActivity : AppCompatActivity() {

    private var txv_points_player_1: TextView? = null
    private var txv_points_player_2: TextView? = null
    private var dialog_settings: Dialog? = null
    private var edt_max_points: EditText? = null
    private var maxPoints: Int = 0
    private var pointsP1: Int = 0
    private var pointsP2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txv_points_player_1 = findViewById(R.id.txv_points_player_1)
        txv_points_player_2 = findViewById(R.id.txv_points_player_2)

        val ln_points_p_1 = findViewById<LinearLayout>(R.id.ln_points_player_1)
        val ln_points_p_2 = findViewById<LinearLayout>(R.id.ln_points_player_2)

        ln_points_p_1.setOnClickListener(clickPointsP1)
        ln_points_p_2.setOnClickListener(clickPointsP2)

        val btn_minus_p_1 = findViewById<LinearLayout>(R.id.btn_minus_player_1)
        val btn_minus_p_2 = findViewById<LinearLayout>(R.id.btn_minus_player_2)

        btn_minus_p_1.setOnClickListener(clickMinusP1)
        btn_minus_p_2.setOnClickListener(clickMinusP2)

        btn_minus_p_1.setOnLongClickListener(longClickPointsP1)
        btn_minus_p_2.setOnLongClickListener(longClickPointsP2)

        dialog_settings = Dialog(this@MainActivity, R.style.customDialog)
        dialog_settings!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_settings!!.setContentView(R.layout.dialog_settings)
        dialog_settings!!.setCanceledOnTouchOutside(true)
        dialog_settings!!.setCancelable(true)

        edt_max_points = dialog_settings!!.findViewById(R.id.dialog_settings_edt_max_points)

        val btn_salvar = dialog_settings!!.findViewById<Button>(R.id.dialog_settings_btn_salvar)
        btn_salvar.setOnClickListener(clickBtnSalvar)

        maxPoints = App.prefs?.getInt(PrefsKeys.MAX_POINTS, 12) ?: 12
        pointsP1 = 0
        pointsP2 = 0
    }

    internal var clickPointsP1: View.OnClickListener = View.OnClickListener {
        if (checkPointsLimit(pointsP1 + 1)) {
            pointsP1++
            updatePointsP1()
        }
    }

    internal var clickPointsP2: View.OnClickListener = View.OnClickListener {
        if (checkPointsLimit(pointsP2 + 1)) {
            pointsP2++
            updatePointsP2()
        }
    }

    internal var longClickPointsP1: View.OnLongClickListener = View.OnLongClickListener {
        pointsP1 += 3
        updatePointsP1()
        false
    }

    internal var longClickPointsP2: View.OnLongClickListener = View.OnLongClickListener {
        pointsP2 += 3
        updatePointsP2()
        false
    }

    internal var clickMinusP1: View.OnClickListener = View.OnClickListener {
        if (pointsP1 > 0) {
            pointsP1--
            updatePointsP1()
        }
    }

    internal var clickMinusP2: View.OnClickListener = View.OnClickListener {
        if (pointsP2 > 0) {
            pointsP2--
            updatePointsP2()
        }
    }

    internal var clickBtnSalvar: View.OnClickListener = View.OnClickListener {
        maxPoints = Integer.parseInt(edt_max_points?.getText().toString())
//        App.getPrefs().edit().putInt(PrefsKeys.MAX_POINTS, maxPoints).apply()
        App.prefs?.edit()?.putInt(PrefsKeys.MAX_POINTS, maxPoints)?.apply()
        dialog_settings?.dismiss()

        Toast.makeText(this@MainActivity, "Configuração salva com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun updatePointsP1() {
        txv_points_player_1?.setText(pointsP1.toString())
    }

    private fun updatePointsP2() {
        txv_points_player_2?.setText(pointsP2.toString())
    }

    private fun checkPointsLimit(points: Int): Boolean {
        if (points > maxPoints) {
            Toast.makeText(this@MainActivity, "Limite de pontos antingido!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.config, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        dialog_settings?.show()
        return false
    }
}
