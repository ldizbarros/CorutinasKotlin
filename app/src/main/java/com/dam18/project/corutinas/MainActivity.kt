package com.dam18.project.corutinas

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dam18.project.corutinas.R.id.miTexto
// extension para referenciar los elementos del layout
import kotlinx.android.synthetic.main.activity_main.*
// para las corutinas
import kotlinx.coroutines.*


class MainActivity : Activity() {

    // Declaramos una variable de tipo Job para cancelar la corutina
    //lateinit var jobCuentaAtras: Job
    // "?" este signo es para poder inicializarla como null
    var jobCuentaAtras: Job? = null
    // para la etiqueta de los logs
    val logtag = "EJEMPLO-->"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Cuando clicamos el boton empieza la CORUTINA cuentaAtras
        // Podemos lanzar varias simultaneamente (ver los logs)
        btnCuentaAtras.setOnClickListener{
            cuentaAtras(it)
        }

        btnCancelar.setOnClickListener{
            // como la variable puede ser null, para no probocar null pointer object
            // kotlin me obliga a comprobar que no es un null
            // asi mi aplicacion no rompe
            if (jobCuentaAtras != null) cancelar(jobCuentaAtras)
            else Log.d(logtag, "jobCuentaAtras es NULL")
        }

    }

    fun cuentaAtras(v: View) {
        ///De esta manera podemos acceder al boton desde la corutina
        //val botonAux = v as Button
        // lanza la corutina en el hilo principal
        //GlobalScope.launch(Dispatchers.Main) {
        // la asignamos a una variable (es de tipo Job) para poder cancelarla
        jobCuentaAtras = GlobalScope.launch(Dispatchers.Default) {
            // cuenta atras 50 a 1
            for (i in 50 downTo 1) {
                // actualiza TextView
                miTexto.text = "$i"
                Log.d(logtag, i.toString())
                // paramos la corutina 0,5sg
                // ATENCIÓN: No para el hilo principal
                delay(500)

            }
            miTexto.text = "Boom!"
        }
    }

    /**
     * Cancela la corutina
     * @param job corutina que queremos cancelar
     * @param job corutina que queremos cancelar, utilizamos "?" para ser coherentes con la declaración
     */
     fun cancelar(job: Job?) {
        job?.cancel()
        Log.d(logtag, "Tarea cancelada")
     }
}
