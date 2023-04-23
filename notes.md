## Fragments: 

Son como Activities pero mas pequeñas que están dentro de una Activity siempre, y se comunican entre ellos mediante la Activity. Nunca directamente. Un fragment va a llamar al activity padre y ese activity va a dar una indicación al segundo fragment. 

El fragment también tiene su propio ciclo de vida.

Entonces es como si tuviéramos dos (o más?) pantallas en una sola y cada una de estas pantallas o sub pantallas tiene su propio ciclo de vida y puede actuar de manera independiente una de otra.

Ejemplo de dos fragments: 

Abajo podríamos tener una lista o un ListView de productos y arriba podríamos tener el detalle del producto seleccionado.

Entonces cuando damos clic en un elemento de la lista, mostrariamos el detalle en el otro fragment de nuestra Activity. 

Nos ayudan a que nuestra aplicación sea mucho mas versatil. 

Son muy utiles y muy usados para aplicaciones para tablets por ejemplo. 

### Ciclo de vida de un fragment: 

Muy similar al de un activity: onCreate(), onStart(), onResume(), onPause(), onStop(), onDestroyView() son compartidos con el ciclo de vida de la activity. 

onAttach(), onCreateView(), onActivityCreated(), onDestroyView(), onDestroy() y onDetach() son propios del ciclo de vida de un fragment. 

OnAttach: se manda a llamar cuando el fragment es creado y se asocia a la activity. 

onCreatedView: nos sirve para crear los views que va a contener el fragment activity

onActivityCreated: se manda a llamar cuando el onCreate del activity termina de ejecutarse

onDestroyView: va a destruir todos los views que creamos en onCreatedView

onDestroy: va a destruir el fragment

onDetach: va a desasociar el fragment del activity. 

----------------------

## Nuevo proyecto: Pokedex para aplicar fragments.

Esta aplicación consta de dos fragments. 

El de abajo es la lista de donde vamos a elegir nuestro pokemon

El de arriba es la imagen y las caracteristicas del pokemos escogido.

1- Armamos nuestra estructura de fragments dentro de nuestro RelativeLayout

2- Desde Java y nuestro paquete principal de app vamos a crear un New Fragment -> Fragment (Blank)

3- Lo voy a llamar ListFragment y doy Finish. Nos va a crear nuestro ListFragment.kt y nuestro fragment_list.xml

4- Voy a quitar de mi ListFragment.kt todo lo que no voy a usar:
- Borro el companion object completo
- Tmp voy a necesitar el metodo onCreate()
- Solo voy a dejar el metodo onCreateView() y lo demas se va. Así quedaría parcialmente. 



```kotlin
class ListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
}
```

5- Ahora vamos a editar el fragment_list.xml que basicamente consiste en meterle un RecyclerView. 

6- Para hacer esto posible debemos agregar la dependencia en nuestro build.gradle: 

```kotlin
implementation 'androidx.recyclerview:recyclerview:1.3.0'
```

Luego sincronizamos y cambiamos el FrameLayout que viene por defecto en nuestro fragment_list por un recyclerview. Queda así: 

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.recyclerview.widget.RecyclerView xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ListFragment" />
```

Listo ya tenemos nuestro ListFragment. 

7- Vamos a armar ahora nuestro segundo fragment llamado: DetailFragment

8- Adaptamos nuevamente nuestro fragment al igual que lo hicimos con el anterior. Solo que en este caso no será el xml un recycler view sino un LinearLayouy de orientation vertical 

(ver .xml y .kt de nuestros fragments en este repo)

9- Luego vamos a indicar en el activity_main.xml que fragment de los que tenemos corresponde a que fragment de los que creamos arriba con el atributo android:name=""

Así quedaría nuestro activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

    </data>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/detail_fragment"
            android:name="com.example.pokemoskotlin.DetailFragment"
            android:layout_width="match_parent"
            android:layout_height="250dp" />

        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/list_fragment"
            android:name="com.example.pokemoskotlin.ListFragment"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/detail_fragment"
            android:layout_alignParentBottom="true"/>

    </RelativeLayout>
</layout>
```
**Dato importante: Para trabajar con Adapters nuestro objetos deben ser data class. No solo class. Dado que sino nos va a arrojar un error en la igualación del oldItem == newItem de la override fun areContentsTheSame() en el companion object de nuestro adapter.**

-----------------------------

***¿Como pasar datos de un fragment a una activity?***

*Regla: No hay que pasar datos entre fragments directamente, sino que debemos pasar los datos de un fragment a una activity y de esta ultima al otro fragment.* 

1- Creo una interface en mi ListFragment a la que voy a llamar PokemonSelectListener {}. 

2- La misma va a tener un metodo dentro que se va a llamar onPokemonSelected(pokemon: Pokemon)

3- Creamos una private lateinit var llamada pokemonSelectedListener: PokemonSelectListener

4- Sobreescribimos luego el metodo del fragment onAttach que es el metodo del fragment cuando se adhiere a la activity. override fun onAttach(context: Context) {}

------------------------

**Stop**

### Breve repaso de interfaces y clases en Kotlin: 

Imagina que estás en un restaurante y tienes que pedir tu comida al mesero. El mesero es como la interface en Kotlin, ya que es la conexión entre tú y la cocina (que sería como una clase). El mesero te presenta un menú con opciones de comida, al igual que una interface te presenta un conjunto de métodos que puedes usar.

Ahora, cuando haces tu pedido al mesero, le dices exactamente qué quieres y cómo lo quieres preparado. Esto es como cuando implementas una interface en una clase en Kotlin. La clase tiene que decirle exactamente qué métodos va a usar y cómo los va a utilizar.

La diferencia entre una interface y una clase en Kotlin es que una interface es como un contrato que obliga a cualquier clase que la implemente a utilizar los métodos que define la interface. Es decir, cualquier clase que implemente una interface debe proporcionar una implementación de todos los métodos que la interface define. Mientras que una clase es una estructura de programación que puede tener variables, métodos, constructores, etc., y no está limitada por un contrato predefinido.

En resumen, una interface es una conexión que define qué métodos se pueden utilizar, mientras que una clase es una estructura que puede definir sus propios métodos y variables.

**Ejemplo:**

Supongamos que estamos desarrollando un juego de carreras de autos. En este juego, tendremos varios tipos de vehículos, como autos deportivos, camiones, motocicletas, etc.

En este caso, podríamos definir una clase llamada "Vehículo" que tenga propiedades como velocidad máxima, aceleración, modelo, etc. Esta clase serviría como base para todos los vehículos en el juego, y cada tipo de vehículo tendría su propia instancia de la clase "Vehículo".

Ahora, supongamos que queremos que todos los vehículos tengan la capacidad de acelerar y frenar. Podríamos definir una interface llamada "MétodosDeConducción" que tenga los métodos "acelerar" y "frenar".

Cada tipo de vehículo tendría que implementar esta interface para poder acelerar y frenar. Por ejemplo, el auto deportivo tendría su propia implementación de los métodos "acelerar" y "frenar", mientras que el camión tendría su propia implementación de estos métodos.

De esta manera, podemos garantizar que todos los vehículos tengan la capacidad de acelerar y frenar, al mismo tiempo que permitimos que cada tipo de vehículo tenga su propia implementación personalizada de estos métodos.

```kotlin
// Definición de la clase Vehículo
class Vehiculo(val velocidadMaxima: Int, val aceleracion: Double, val modelo: String) {
    // Aquí irían las propiedades y métodos específicos de cada tipo de vehículo
}

// Definición de la interface MétodosDeConducción
interface MétodosDeConducción {
    fun acelerar()
    fun frenar()
}

// Definición de la clase AutoDeportivo que hereda de la clase Vehiculo y implementa la interface MétodosDeConducción
class AutoDeportivo(velocidadMaxima: Int, aceleracion: Double, modelo: String) : Vehiculo(velocidadMaxima, aceleracion, modelo), MétodosDeConducción {
    override fun acelerar() {
        // Implementación específica para el auto deportivo
    }

    override fun frenar() {
        // Implementación específica para el auto deportivo
    }
}

// Definición de la clase Camión que hereda de la clase Vehiculo y implementa la interface MétodosDeConducción
class Camion(velocidadMaxima: Int, aceleracion: Double, modelo: String) : Vehiculo(velocidadMaxima, aceleracion, modelo), MétodosDeConducción {
    override fun acelerar() {
        // Implementación específica para el camión
    }

    override fun frenar() {
        // Implementación específica para el camión
    }
}
```

*Este código define la clase Vehiculo que tiene propiedades comunes a todos los vehículos, como velocidad máxima, aceleración y modelo. También define la interface MétodosDeConducción que tiene los métodos acelerar() y frenar().*

*Luego, se definen las clases AutoDeportivo y Camion heredan las propiedades y métodos de la clase Vehiculo, y al mismo tiempo, implementan la interface MétodosDeConducción. Así, ambas clases tienen acceso a las características comunes de todos los vehículos y también pueden acelerar y frenar.*

-------------------------

Volvamos ahora si al paso de datos de un fragment a una activity. Nos habiamos quedado en el punto 4. Por lo que retomamos desde ahí: 

Quedaría así todo lo agregado en los 4 puntos de arriba a mi ListFragment: 

```kotlin
class ListFragment : Fragment() {

    interface PokemonSelectListener {
        fun onPokemonSelected(pokemon: Pokemon)
    }

    private lateinit var  pokemonSelectListener: PokemonSelectListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pokemonSelectListener = try {
            context as PokemonSelectListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement PokemonSelectListener")
        }
    }
    // ... more code
}
```
Este codigo que sumamos lo que hace es obligar a MainActivity a implementar PokemonSelectListener y sino lo hace entonces la app se va a romper y nos va a arrojar una excepción de tipo ClassCastException

Por lo que debemos hacer entonces en MainActivity esta implementación. Lo cual significa que debe heredar esta interface ademas de heredar lo que ya hereda.  

Esto nos va a obligar a implementar el metodo de nuestra interface llamado onPokemonSelected(pokemon: Pokemon)

Luego vamos a modificar la acción del adapter.onItemClickListener, actualmente arroja un Toast, pero vamos a hacer otra cosa en su lugar. 

De esta manera se va a ejecutar el metodo que acabamos de sobreescribir en MainActivity y esta es una de las formas de pasar datos de un fragment a una activity. 

Existe una forma alternativa que vamos a poner y luego comentar: 

Creo en MainActivity una función a la que voy a llamar iAmYourFather(pokemon: Pokemon) {}

Luego en ListFragment donde damos click al pokemon escribimos: 
(activity as MainActivity).iAmYourFather(it)

Así quedaría el ListFragment con las dos alternativas: 

```kotlin
class ListFragment : Fragment() {

    interface PokemonSelectListener {
        fun onPokemonSelected(pokemon: Pokemon)
    }

    private lateinit var  pokemonSelectListener: PokemonSelectListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pokemonSelectListener = try {
            context as PokemonSelectListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement PokemonSelectListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentListBinding.inflate(inflater)

        val recycler = view.pokemonRecycler

        // Para el layout manager en la activity pasabamos this como contexto. Pero el fragment no tiene contexto por si solo.
        // Debemos pedirle el contexto a la activity
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        // Challenge: Crear el adaptar para nuestro recycler!!!
        val adapter = PokemonAdapter()

        view.pokemonRecycler.adapter = adapter

        adapter.onItemClickListener = {
            pokemonSelectListener.onPokemonSelected(it)
            (activity as MainActivity).iAmYourFather(it)
        }
        // ... more code
    }
}
```

Así quedaría el MainActivity con las dos formas: 

```kotlin
class MainActivity : AppCompatActivity(), ListFragment.PokemonSelectListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPokemonSelected(pokemon: Pokemon) {
        TODO("Not yet implemented")
    }

    fun iAmYourFather(pokemon: Pokemon) {
        TODO("Not yet implemented")
    }
}
```

**Esta última forma solo sirve si el fragment lo vamos a usar en una única activity. Si vamos a reusar nuestro fragment en mas de una activity entonces los que debemos hacer es usar la primer forma que consiste en sobreescribir el metodo de nuestra interface creada en el fragment en cada una de las activities en donde sea utilizada.**

Yo voy a dejar ahora solo la forma con interface que es la que serviría para mas de una activity. 

----------------------

Ahora que ya pasamos los datos del Fragment a la Activity tenemos que ver como pasamos los datos de nuestra activity al otro fragment que era nuestro objetivo inicial: 

1- Nuestro otro fragment se llama Detail Fragment y allí también vamos a crear una val view, dodo que returnabamos la misma en linea hasta acá. Y luego retornamos la misma

2- Voy a crear 5 private lateinit var, 1 por cada uno de los elementos que debo pintar en el view. En este caso son imageView: ImageView, hpText: TextView, attackText: TextView, defenseText: TextView, speedText: TextView. Estas variables deben ser de alcance global. Es decir, deben estar fuera del metodo onCreateView().

3- Luego las voy a inicializar dentro del metodo onCreateView()

4- Creo un metodo publico al que en este caso voy a llamar setPokemonData(pokemon: Pokemon){} donde voy a definir los textos de cada una de mis views con los datos de mi objeto pokemon que traigo a este fragment

5- En MainActivity voy a declarar una variable como private lateinit var, de alcance global en la clase, private lateinit var detailFragment: DetailFragment

6- En el metodo onCreate voy a inicializar al detailFragment. 
A- Lo puedo hacer con supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment ó
B- ¿Como dataBinding? Descubrir // Deje pregunta en el curso. Pero por mí cuenta no he encontrado la forma. 

7- Luego vamos a pasar el pokemon que recibimos en el metodo de MainActivity onPokemonSelected(pokemon: Pokemon) al detailFragment que acabamos de instanciar en el paso de arriba a traves del uso del metodo del detail fragment llamado setPokemonData(pokemon)

Así quedaría entonces el DetailFragment: 

```kotlin
package com.example.pokemoskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pokemoskotlin.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var hpText: TextView
    private lateinit var attackText: TextView
    private lateinit var defenseText: TextView
    private lateinit var speedText: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentDetailBinding.inflate(inflater)

        imageView = view.fragmentDetailImage
        hpText = view.fragmentDetailHp
        attackText = view.fragmentDetailAttack
        defenseText = view.fragmentDetailDefense
        speedText = view.fragmentDetailSpeed

        return view.root
    }

    fun setPokemonData(pokemon: Pokemon) {
        hpText.text = pokemon.hp.toString()
        attackText.text = pokemon.attack.toString()
        defenseText.text = pokemon.defense.toString()
        speedText.text = pokemon.speed.toString()
    }
}
```

Así quedaría entonces el MainActivity: 

```kotlin
class MainActivity : AppCompatActivity(), ListFragment.PokemonSelectListener {

    private lateinit var detailFragment: DetailFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment
    }

    override fun onPokemonSelected(pokemon: Pokemon) {
        detailFragment.setPokemonData(pokemon)
    }
}
```

Finalmente vamos a darle a las TextView´s donde mostramos el hp, ataque, defensa y velocidad un formato en el que no solo mostremos el numero sino también que estamos mostrando. 

Para eso vamos a crear los formatos en strings.xml así

```xml
<resources>
    <string name="app_name">Pokemos Kotlin</string>
    <string name="hp_format">Hp: %d</string>
    <string name="attack_format">Attack: %d</string>
    <string name="defense_format">Defense: %d</string>
    <string name="speed_format">Speed: %d</string>
</resources>
```

Luego vamos a DetailFragment y editamos el momento en el que asignamos los valores así: 

```kotlin
fun setPokemonData(pokemon: Pokemon) {
    hpText.text = getString(R.string.hp_format, pokemon.hp)
    attackText.text = getString(R.string.attack_format, pokemon.attack)
    defenseText.text = getString(R.string.defense_format, pokemon.defense)
    speedText.text = getString(R.string.speed_format, pokemon.speed)
    }
```

Bien. Con esto ya muestra el Hp, attack, defense y speed, en función del pokemos que escojamos pero aún seguimos con la imagen hardcodeada y sin sonido. 

Vamos entonces a usar Glide para traer imagenes desde internet...

-----------------------------

Para traer imagenes de internet en Android se usa Glide o en su defecto Picasso. 

**Glade:**

La documentación de glide podemos encontrarla acá: 

https://github.com/bumptech/glide

1- Debemos importar Glide en el fragment en el cual vayamos a cargar nuestra imagen que traeremos de internet.

2- Luego ubicaremos a Glide dentro del metodo de nuestro fragment que se ocupre de asignar valores a las View´s que conforman el fragment

3- Las imagenes deben estar pre procesadas y tener un tamaño fijo cuando vas a hacer un proyecto de verdad. En este caso vamos a buscar imagenes horizontales solamente. 

4- Dentro del load cargo entonces la propiedad de mi objeto donde guardo el enlace a la foto de internet. 

**IMPORTANTISIMO: Si no agregamos el permiso de internet en el manifest o no va a traer nuestras fotos o lo hará de forma muy lenta...**

5- Agrego en el manifiest el permiso de internet así: 

```xml
 <uses-permission android:name="android.permission.INTERNET" />
```

La primera que hagamos click sobre un pokemon va a tardar un poco dado que descarga la imagen. Pero una vez que la tenemos descargada la misma queda en la cache por lo que luego si volvemos a seleccionar el mismo pokemon no tendremos problemas de demora. 

-------------------------

**Manejando "carga" y errores en Glade:** 

1- Vamos a ir a donde estamos implementando Glide. En este caso al DetailFragment en su metodo setPokemonData() {}

2- Vamos a reemplazar el ".into(imageView) por .listener(object: RequestListener< Drawable >{}).into(imageView)

3- Me marcará en rojo "object" y si posiciono en el me sugerirá "implementar miembros". Son dos metodos de la interface RequestListener los que debo implementar para luego editar. 

4- El primer metodo va a aplicar cuando falle la carga de nuestra imagen. El segundo metodo va a aplicar cuando termine de cargarse la imagen finalmente. 

5- Entonces aprovechando el segundo metodo voy a ir al fragment_layout.xml y voy a meter a mi ImageView dentro de un FrameLayout donde va a convivir con una ProgressBar y se mostrará esta ultima hasta tanto la imagen esté cargada. Cuando esto ultimo ocurra voy a ocultar la progress bar y voy a mostrar la image view. 

6- Voy a crear mi private lateinit var loadingWheel en mi DetailFragment.kt y luego voy a inicializarla en el onCreateView()

7- Entonces apenas invocamos a setPokemonData(), es decir, cuando queremos cargar una imagen vamos a cambiar la visibilidad de loadingWheel de GONE a VISIBLE y luego, cuando termina la carga ( onResourceReady() ) vamos a volver a ocultarla. Si la descarga falló también la ocultamos de nuevo. 

8- onLoadFailed & onResourceReady te piden que devuelvas un boolean para hacer mas cosas con target, pero por el momento no las vamos a hacer. Por lo que vamos a retornar un false para que compile en ambos metodos sobre escritos. 

9- Adicionalmente, cuando la descarga falle, POR UNA EXCEPCION, vamos a mostrar una imagen por default para estos casos.

10- Si quiero manejar también la posibilidad de que la descarga falle porque la url era incorrecta entonces debemos hacer una modificación... Quitamos al final el ".into(imageView)" y agregamos ".error(R.drawable.ic_image_not_supported_black).into(imageView)"

Así queda entonces nuestro codigo del DetailFragment con la implementación completa de Glide, manejo de carga, errores y excepciones: 

```kotlin
class DetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var hpText: TextView
    private lateinit var attackText: TextView
    private lateinit var defenseText: TextView
    private lateinit var speedText: TextView
    private lateinit var loadingWheel: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentDetailBinding.inflate(inflater)

        imageView = view.fragmentDetailImage
        hpText = view.fragmentDetailHp
        attackText = view.fragmentDetailAttack
        defenseText = view.fragmentDetailDefense
        speedText = view.fragmentDetailSpeed
        loadingWheel = view.loadingWheel

        return view.root
    }

    fun setPokemonData(pokemon: Pokemon) {

        loadingWheel.visibility = View.VISIBLE
        // Podemos usar this, porque Glide admite fragments:
        Glide.with(this).load(pokemon.imageUrl)
            .listener(object: RequestListener<Drawable>{

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingWheel.visibility = View.GONE
                    imageView.setImageResource(R.drawable.ic_image_not_supported_black)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingWheel.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_image_not_supported_black)
            .into(imageView)

        hpText.text = getString(R.string.hp_format, pokemon.hp)
        attackText.text = getString(R.string.attack_format, pokemon.attack)
        defenseText.text = getString(R.string.defense_format, pokemon.defense)
        speedText.text = getString(R.string.speed_format, pokemon.speed)
    }
}
```

------------------------------

**Picasso:** 

Picasso es otra de las librerías más utilizadas en Android para descargar fotos fácilmente.

Puedes encontrar la documentación de la librería aquí: https://square.github.io/picasso/

-------------------------------

Ahora vamos a aprender a agregar sonidos para nuestros pokemones. En realidad vamos a aprender a agregar sonidos a  cualquier aplicación con esto: 

1- Vamos "res", botón derecho, New, Android Resource Directory y en resource type vamos a seleccionar "raw" y le dejamos ese mismo nombre que viene por default. 

2- En la carpeta raw es donde van a estar los sonidos. 

3- Lleno la carpeta raw con todos los sonidos que necesito para mis pokemones. Esto se puede hacer directamente pegandolos (ctrl+v) dentro de la carpeta "raw". 

4- Para poder obtener los sonidos de un pokemon debemos hacerlo igual que como hacemos con los dibujos, es decir, desde R.raw.nombre_sonido

5- Sumo el atributo sonido de tipo Int en mi clase pokemon y luego en DetailFragment, en el metodo setPokemonData() {} es donde vamos a ejecutar ese sonido. 

6- Para esto vamos a tener que crear una val mediaPlayer

7- Una completa la variable vamos poner mediaPlayer.start() y eso hará que se reproduzca el sonido al seleccionar un pokemon de la lista. 

Veamos entonces como queda el codigo del DetailActivity: 

```kotlin
class DetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var hpText: TextView
    private lateinit var attackText: TextView
    private lateinit var defenseText: TextView
    private lateinit var speedText: TextView
    private lateinit var loadingWheel: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentDetailBinding.inflate(inflater)

        imageView = view.fragmentDetailImage
        hpText = view.fragmentDetailHp
        attackText = view.fragmentDetailAttack
        defenseText = view.fragmentDetailDefense
        speedText = view.fragmentDetailSpeed
        loadingWheel = view.loadingWheel

        return view.root
    }

    fun setPokemonData(pokemon: Pokemon) {

        loadingWheel.visibility = View.VISIBLE
        // Podemos usar this, porque Glide admite fragments:
        Glide.with(this).load(pokemon.imageUrl)
            .listener(object: RequestListener<Drawable>{

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingWheel.visibility = View.GONE
                    imageView.setImageResource(R.drawable.ic_image_not_supported_black)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingWheel.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_image_not_supported_black)
            .into(imageView)

        hpText.text = getString(R.string.hp_format, pokemon.hp)
        attackText.text = getString(R.string.attack_format, pokemon.attack)
        defenseText.text = getString(R.string.defense_format, pokemon.defense)
        speedText.text = getString(R.string.speed_format, pokemon.speed)

        val mediaPlayer = MediaPlayer.create(requireActivity(), pokemon.soundId)
        mediaPlayer.start()
    }
}
```

-------------------------------

**Agregando soporte para Fragment Navigation:**























