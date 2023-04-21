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








