package com.example.cakecontest;

//Características importadas
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import static android.view.View.*;
import static android.widget.Toast.LENGTH_LONG;

/*Código java para al App, controla los eventos y la introducción de datos del usuario
*que este introduce en los campos para su inscripción en el concurso.
* Se ha estructurado el código a traves de metodos que revisan cada uno de los campos
* a rellenar. Al pulsar el botón "Inscribirme" el código captura el evento, entonces estos
* se comparan para saber si el usuario a introducido datos. Lo primero que hace el código
* es revisar si el participante cumple con la edad mímima requerida, para ello compara primero
* el campo edad para verificar si hay algún dato y despues, en caso de que si lo haya,
* captura ese dato en una variable temporal. Si no lo hay, se notifica al usaurio con un Toast
* y se cambia el color del campo edad a rojo. Se da por hecho que el dato es numérico
* ya que el campo edad está creado solo para este tipo de datos.
* Una vez que se verifica la edad y si esta cumple el requisito, en una sentencia condicional if
* se llaman al resto de métodos y se examinan los datos de cada uno de los campos, se evalua si
* tienen datos y se capturan a variables temporales. Una vez que que se ha verificado, si todos
* tienen datos, se llama al constructor de la clase Player a traves de una arrayList para almacenar
* al nuevo objeto player con los datos capturados en la variables temporales, se informa al usuario
* de que la inscripión se ha producido correctamente y se finaliza el cuestionario reiniciando los
* campos y el layout a su estad inicial mediante la función finalizar.
* En el caso de que algún dato no tenga datos, se informa al usuario mediante un Toast y se
* cambia el color del fondo del campo a rojo, simpre en sentido por orden descendente, si tiene
* los campos apellido y sexo vacíos, solo se cambiará de color el primero, y en caso de que lo
* rellene y vuelva a pulsar el botón, se cambiará de color el segundo que dejó sin rellenar.
* Los campos vuelven a su color cuando se pulsa sobre ellos y el teclado está activo.
* El usuario puede cancelar la inscripción en cualquier momento desde el botón cancelar.
*
*
*/



public class MainActivity extends AppCompatActivity {

    // variables relacionadas con los objetos xml.
    EditText name;
    EditText secName;
    RadioGroup groupGenter;
    RadioButton radioMan;
    RadioButton radioWoman;
    EditText age;
    ToggleButton toggle;
    EditText contest;
    Button enroll;
    Button cancel;

    //ArrayList para memorizar los objetos Player
    ArrayList<Player> players = new ArrayList<>();

    //Variables temporales.
    String stringName, stringSecName, stringGender, oldContest = "";
    int intAge;
    Boolean oldPlayer =false;
    int redColor = Color.parseColor("#B40000");
    int backColor = Color.parseColor("#517278");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        //Enlaces de los objetos xml a las variables java.
        name = findViewById(R.id.name);
        secName = findViewById(R.id.secName);
        groupGenter = findViewById(R.id.groupGenter);
        radioMan = findViewById(R.id.radioMan);
        radioWoman = findViewById(R.id.radioWoman);
        age = findViewById(R.id.age);
        toggle = findViewById(R.id.toggle);
        contest = findViewById(R.id.contest);
        enroll = findViewById(R.id.enroll);
        cancel = findViewById(R.id.cancel);
    }

    //Evento principal, llama al resto de métodos para realizar las comprobaciones de datos.
    public void dataPlayer(View view){

        //Se llama al método ageValue para evaluar la edad y obtener la condición para seguir o no.
        if (ageValue(false)) {

           //Si la edad cumple con el mínimo, se llama al resto de eventos y se espera su respuesta para evaluzar la condición. Todas deben ser true.
            if (nameValue(false) && secNameValue(false) && genderValue(false) && oldPlayer(false)) {

                //En el caso de que todos lo datos estén introducidos, se procede a generar el objeto mediante el constructor de la clase player
                // y almacenarlo en el ArrayList.
                players.add(new Player(stringName, stringSecName, stringGender, intAge, oldPlayer, oldContest));

                //Se muestra un toast al usuario informando de que la inscripción ha sido completada.
                Toast.makeText(this, "Inscripción completada. Gracias.", Toast.LENGTH_SHORT).show();
                finish(view); // se finaliza el cuestionario llamando al metodo finish.

            } else {

                //En el caso de que no se cumpla alguno de los condicionales, se informa al usuario con un toast de que faltan campos por rellenar.
                Toast.makeText(this, "Faltan datos por rellenar", LENGTH_LONG).show();
            }
        }
    }


    //Methods

    //
    //Metodo para evaluar la Edad, se verifica la captura de datos en el campo, si existen, se da por hecho que son numéricos
    // por el tipo de campo y se evalua la edad àra saber si cumple con el mínimo.
    public boolean ageValue(Boolean ageBool){

        //Se captura el texto para saber si hay datos.
        if(!(this.age.getText().toString().equals(""))){

            //Si los hay, se evalua si la edad es la mínima
            if (Integer.parseInt(this.age.getText().toString()) >= 18){

                //Se devuelve al color original el contenedor por si ha modificado por un error controlado.
                this.age.setBackgroundColor(backColor);

                //Se captua el dato a una variable temporal y se modifica la variable local a true.
                intAge = Integer.parseInt(this.age.getText().toString());
                ageBool = true;

            } else {

                //En caso de que no se cumpla se muestra un toas al usuario informando de ellos.
                Toast.makeText(this, "Lo sentimos, la edad mínima para participar se de 18 años.", LENGTH_LONG).show();
                this.age.setBackgroundColor(redColor);
            }

        } else {

            //En caso de que no hayan datos, se informa al usuario de ello con un toas y se cambia el color del contenedor a rojo.
            Toast.makeText(this, "Faltan datos por rellenar", LENGTH_LONG).show();
            this.age.setBackgroundColor(redColor);

        }

        //Se devuelve el valor de la condición
        return ageBool;
    }

    //
    //Método para evaluar si hay datos en el contenedor nombre.
    public boolean nameValue(Boolean nameBool){

        //Se devuelve al color original el contenedor por si se ha modificado por un error controlado.
        this.name.setBackgroundColor(backColor);

        //Se examina si hay datos en eon contenedor nombre.
        if (!(name.getText().toString().equals(""))){

            //Si los hay, se capturan a una variable temporal se pasa a true la variable local
            stringName = name.getText().toString();
            nameBool = true;

        } else {

            //En caso de que no se cumpla, se cambia el color del contendor nombre
            this.name.setBackgroundColor(redColor);
        }

        //Se devuelve el valor de la condición
        return nameBool;
    }

    //
    //Método para evaluar el contenedor apellido
    public boolean secNameValue(Boolean secNameBool){

        //Se devuelve al color original el contenedor por si se ha modificado por un error controlado.
        this.secName.setBackgroundColor(backColor);

        //Se evalua si hay datos.
        if (!(secName.getText().toString().equals(""))){

            //Si los hay, se capturan a una variable temporal y se cambia a true la variable local
            stringSecName =secName.getText().toString();
            secNameBool = true;

        } else {

            //En caso de que no se cumpla, se cambia el color del contendor apellido
            this.secName.setBackgroundColor(redColor);
        }

        //Se devuelve el valor de la condición
        return secNameBool;
    }

    //
    //Se evalua si uno de los dos RadioButton está activado.
    public boolean genderValue(Boolean genderBool){

        //Se devuelve al color original el contenedor por si se ha modificado por un error controlado.
        this.groupGenter.setBackgroundColor(backColor);

        //Si este está activo
        if (radioMan.isChecked()){

            //Se cambia el valor de la variable temporal y se modifica la variable local a true.
            stringGender = "Masculino";
            genderBool = true;

        //Si es este el que está activo
        } else if(radioWoman.isChecked()){

            //Se cambia el valor de la variable temporal y se modifica la variable local a true.
            stringGender = "Femenino";
            genderBool = true;

            //Si ninguno está activo
        } else {

            //Se cambia el color del RadioGroup que los contiene a rojo
            this.groupGenter.setBackgroundColor(redColor);
        }

        //Se devuleve el valor de la condición
        return genderBool;
    }

    //
    //Se evalua si el usuario ha contestado a la pregunta con un si mediante el botón switch
    public boolean oldPlayer(Boolean oldPlayerBool){

        //Se cambia el valor de la variable temporal y se modifica la variable local a true.
        this.contest.setBackgroundColor(backColor);

        //Si ha respondido "si", se evalua que haya introducido datos en el nuevo contenedor que se muestra.
        if (toggle.isChecked() && !(contest.getText().toString().equals(""))){

            //Si lo ha hecho, se capturan los datos a una variable temporal, se cambia a true
            //la variable temporal y la variable local
            oldContest = contest.getText().toString();
            this.oldPlayer = true;
            oldPlayerBool = true;

        //Si la deja sin contestar
        } else if (!(toggle.isChecked())) {

           //Se cambia la variable local a true, para verificar que el contenedor es correcto.
            oldPlayerBool = true;

        } else {

            //Si ninguna condición es cierta, se cambia el contenedor a rojo.
            this.contest.setBackgroundColor(redColor);
        }

        //Se devuelve el valor de la condición
        return oldPlayerBool;
    }

    //Se escucha el evento del botón switch
    public void yesContest(View view){
        if(toggle.isChecked()) { //Si está pulsado
            contest.setVisibility(VISIBLE); //Se muestra un nuevo contenedor
        } else { //Si deja de estarlo
            contest.setText(""); //Se boran los posibles datos que se pudiesen haber introducido
            contest.setVisibility(GONE); //Se esconde el contenedor
        }
    }

    //Al hacer click en un contenedor, si alguno de ellos están e rojo por u fallo controlado, se devolvera al color original
    public void onWhite(View view){
        age.setBackgroundColor(backColor);
        name.setBackgroundColor(backColor);
        secName.setBackgroundColor(backColor);
        groupGenter.setBackgroundColor(backColor);
        contest.setBackgroundColor(backColor);
    }

    //Para finalizar el código, se llama a este evento, que borra los datos de los contenedores y reestable
    //el menú
    public void finish(View view){
        onWhite(view);
        name.setText("");
        secName.setText("");
        age.setText("");
        toggle.setChecked(false);
        contest.setText("");
        contest.setVisibility(GONE);
          }

    //Para cancelar se llama a este método que muestra la cancelación al usuario y llama al método finalizar.
    public void cancel(View view){
        Toast.makeText(this, "Inscripción cancelada", LENGTH_LONG).show();
        finish(view);
    }

}