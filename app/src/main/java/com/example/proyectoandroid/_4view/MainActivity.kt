package com.example.proyectoandroid._4view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.proyectoandroid.R
import com.example.proyectoandroid.databinding.ActivityMainBinding
import com.example.proyectoandroid._0core.hide
import com.example.proyectoandroid._0core.show
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.model.CategoriaComplementoEntity
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity
import com.example.proyectoandroid._1data.model.ComplementosEntity
import com.example.proyectoandroid._1data.model.ProductoEntity

class MainActivity : AppCompatActivity() {
    private lateinit var database: AppDataBase
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= AppDataBase.getInstanceDatabase(this)

        agregar_categoria()
        agregar_productos()
        agregar_categoria_complemento()
        agregar_complemento()

/*
* Accedo al fragment manager mediante (supportFragmentManager) para encontrar el fragmento es nav_host_fragment
* Luego creo el navController
* */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        observeDestinationChange()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {

                R.id.loginFragment -> {
                    binding.bottomNavigationView.hide()
                }

                R.id.registerFragment -> {
                    binding.bottomNavigationView.hide()
                }
                else -> {
                    binding.bottomNavigationView.show()
                }
            }
        }
    }
    private fun agregar_productos() {
        val producto1= ProductoEntity(
            1,
            "Taco Mixto",
            "Taco de Tortilla, frejol, pollo deshilachado, chorizo, ensalada, papas al hilo, cremas a elecciòn",
            22.00,
            100,
            0.0,
            0.0,
            "data:image/jpeg;base64,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",
            1,
            1
        )
        producto1.calcular_precio_final()
        val producto2= ProductoEntity(
            2,
            "Taco Especial",
            "Taco de Tortilla, frejol, pollo deshilachado, chorizo, huevo, ensalada, papas al hilo, cremas a elecciòn",
            23.00,
            100,
            0.0,
            0.0,
            "data:image/jpeg;base64,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",
            1
        )
        producto2.calcular_precio_final()

        val producto3= ProductoEntity(
            3,
            "Taco de Pollo",
            "Taco de tortilla, frejol, pollo deshilachado, ensalada, papas al hilo, cremas a elección",
            19.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366590-1593529026083.jpg?d=437x375&?d=1920xundefined&e=webp",
            1
        )
        producto3.calcular_precio_final()

        val producto4= ProductoEntity(
            4,
            "Taco Chacarrero",
            "Taco de tortilla, frejol, pollo deshilachado, chorizo, pavo, lechón, ensalada, papas al hilo, cremas a elección",
            25.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366609-1592513815558.jpg?d=437x375&?d=1920xundefined&e=webp",
            1
        )
        producto4.calcular_precio_final()

        val producto5= ProductoEntity(
            5,
            "Taco de Pavo",
            "Taco de tortilla, frejol, pavo, ensalada, papas al hilo, cremas a elección",
            22.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366615-1593529024159.jpg?d=437x375&?d=1920xundefined&e=webp",
            1
        )
        producto5.calcular_precio_final()

        val producto6= ProductoEntity(
            6,
            "Taco de Chorizo",
            "Taco de tortilla, frejol chorizo, ensalada, papas al hilo, cremas a elección",
            19.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366619-1592513814453.jpg?d=437x375&?d=1920xundefined&e=webp",
            1
        )
        producto6.calcular_precio_final()

        val producto7= ProductoEntity(
            7,
            "Enchilada Mixta",
            "Enchilada de tortilla, queso, pollo deshilachado, chorizo, cremas a elección, ensalada, papas al hilo",
            22.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366622-1592513502670.jpg?d=437x375&?d=1920xundefined&e=webp",
            2
        )
        producto7.calcular_precio_final()

        val producto8= ProductoEntity(
            8,
            "Enchilada Especial",
            "Enchilada de tortilla, queso, pollo deshilachado, chorizo, huevo, ensalada, papas al hilo, cremas a elección",
            23.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366624-1608678281445.png?d=437x375&?d=1920xundefined&e=webp",
            2
        )
        producto8.calcular_precio_final()

        val producto9= ProductoEntity(
            9,
            "Enchilada Chacarrera",
            "Enchiladas de tortilla, queso chorizo, pollo deshilachado, pavo, lechón, ensalada, papas al hilo, cremas a elección",
            25.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366625-1592513499332.jpg?d=437x375&?d=1920xundefined&e=webp",
            2
        )
        producto9.calcular_precio_final()

        val producto10= ProductoEntity(
            10,
            "Enchilada de Pavo",
            "Enchilada de tortilla, queso, pavo, ensalada, papas al hilo, cremas a elección",
            22.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366626-1593528707782.jpg?d=437x375&?d=1920xundefined&e=webp",
            2
        )
        producto10.calcular_precio_final()

        val producto11= ProductoEntity(
            11,
            "Enchilada de Chorizo",
            "Enchilada de tortilla, queso, chorizo, ensalada, papas al hilo, cremas a elección",
            19.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366627-1592514360249.jpg?d=437x375&?d=1920xundefined&e=webp",
            2
        )
        producto11.calcular_precio_final()

        val producto12= ProductoEntity(
            12,
            "Sándwich de Pavo",
            "Sándwich de pavo, pan, ensalada, papas al hilo, cremas a elección",
            19.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366630-1608678076120.jpg?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto12.calcular_precio_final()

        val producto13= ProductoEntity(
            13,
            "Sándwich Hod Dog",
            "Sándwich de 2 hot dogs, pan, ensalada, papas al hilo, cremas a elección",
            13.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366631-1593528882595.jpg?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto13.calcular_precio_final()

        val producto14= ProductoEntity(
            14,
            "Sándwich de Chorizo",
            "Sándwich de chorizo, pan, ensalada, papas al hilo, cremas a elección",
            15.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366632-1592513400187.jpg?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto14.calcular_precio_final()

        val producto15= ProductoEntity(
            15,
            "Sándwich de Hamburguesa",
            "Sándwich de carne de hamburguesa, pan, ensalada, papas al hilo, cremas aparte y a elección",
            15.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366633-1608678160727.jpg?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto15.calcular_precio_final()

        val producto16= ProductoEntity(
            16,
            "Sándwich de Pollo",
            "Sándwich de pollo deshilachado, pan, ensalada, papas al hilo, cremas a elección",
            16.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366634-1608678026595.png?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto16.calcular_precio_final()

        val producto17= ProductoEntity(
            17,
            "Sándwich de Filete",
            "Sándwich de filete de pollo, pan, ensalada, papas al hilo, cremas a elección",
            16.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366635-1593528882585.jpg?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto17.calcular_precio_final()

        val producto18= ProductoEntity(
            18,
            "Sándwich de Milanesa",
            "Sándwich de milanesa de pollo, pan, ensalada, papas al hilo, cremas a elección",
            16.00,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/366637-1608678118094.jpg?d=437x375&?d=1920xundefined&e=webp",
            3
        )
        producto18.calcular_precio_final()

        val producto19= ProductoEntity(
            19,
            "Salchipapas Clásica",
            "Salchipapas, hot dog frankfurter, papas fritas crocantes y 3 salsas a elección",
            16.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/863258-1621535130579.png?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto19.calcular_precio_final()

        val producto20= ProductoEntity(
            20,
            "Choripapas",
            "Papitas fritas con rodajas de chorizo y 3 salsas a elección",
            18.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/59be6ab6-a129-4375-8fb6-831362014b6c-1621551246909.jpeg?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto20.calcular_precio_final()

        val producto21= ProductoEntity(
            21,
            "Salchimixto",
            "Salchipapas, trozos de chorizo, hot dog frankfurter y 3 salsas a elección",
            22.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/863261-1621535130579.png?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto21.calcular_precio_final()

        val producto22= ProductoEntity(
            22,
            "Salchibroaster",
            "Salchipapas, alita broaster, hot dog frankfurter papas frita crocantes y 3 salsas a elección",
            22.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/863263-1621535130579.png?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto22.calcular_precio_final()


        val producto23= ProductoEntity(
            23,
            "Salchiroyal",
            "Salchipapa royal con hotdog frankfurter, queso edam, huevo frito, jamón inglés y crujiente papas fritas, cremas a elección",
            22.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/863264-1621535130580.png?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto23.calcular_precio_final()

        val producto24= ProductoEntity(
            24,
            "Salchiwokk",
            "Salchipapas, huevos revueltos, hot dog frankfurter, trozos de pollo picante y 3 salsas a elección",
            22.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/5c85332a-b063-4332-ab56-6e4eed15bcc1-1621551261578.jpeg?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto24.calcular_precio_final()

        val producto25= ProductoEntity(
            25,
            "Salchialitas Picantes",
            "Salchipapas, hot dog frankfurter, alitas picantes, papas fritas picantes y 3 salsas a elección",
            24.90,
            100,
            0.0,
            0.0,
            "https://images.rappi.pe/products/863266-1621535130589.png?d=437x375&?d=1920xundefined&e=webp",
            4
        )
        producto25.calcular_precio_final()



        val list=ArrayList<ProductoEntity>().apply {
            add(producto1)
            add(producto2)
            add(producto3)
            add(producto4)
            add(producto3)
            add(producto4)
            add(producto4)
            add(producto5)
            add(producto6)

            add(producto7)
            add(producto8)
            add(producto9)
            add(producto10)
            add(producto11)

            add(producto12)
            add(producto13)
            add(producto14)
            add(producto15)
            add(producto16)
            add(producto17)
            add(producto18)

            add(producto19)
            add(producto20)
            add(producto21)
            add(producto22)
            add(producto23)
            add(producto24)
            add(producto25)
        }
        database.productDao().insertManyProducts(list)

    }

    private fun agregar_categoria(){
        val categoryProducto1 = CategoriaProductoEntity(id_categ = 1, nombre_categ = "Tacos",
            img_categ = "https://media-cdn.tripadvisor.com/media/photo-s/16/cc/3b/c4/enchilada-de-carne-4.jpg")
        val categoryProducto2 = CategoriaProductoEntity(id_categ = 2, nombre_categ = "Enchilada",
            img_categ = "https://images-gmi-pmc.edge-generalmills.com/0f58d310-021e-4175-b1b6-d73be26bac7e.jpg")
        val categoryProducto3 = CategoriaProductoEntity(id_categ = 3, nombre_categ = "Hamburguesa",
            img_categ = "https://www.elespectador.com/resizer/EfBdvKaMmD23gBgnU5rIQJ1BXcU=/657x0/filters:format(jpeg)/cloudfront-us-east-1.images.arcpublishing.com/elespectador/XQAAMAXBUFFTTONUKMAMMOYOOA.jpg")
        val categoryProducto4 = CategoriaProductoEntity(id_categ = 4, nombre_categ = "SalchiPapa",
            img_categ = "data:image/jpeg;base64,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")

        val list=ArrayList<CategoriaProductoEntity>().apply {
            add(categoryProducto1)
            add(categoryProducto2)
            add(categoryProducto3)
            add(categoryProducto4)
        }

        database.categoryProductDao().insertManyCategorysProduct(list)

    }

    private fun agregar_categoria_complemento(){
        val categoryProducto1 = CategoriaComplementoEntity(
            1,
            "Guarnicion"
        )
        val categoryProducto2 = CategoriaComplementoEntity(
            2,
            "Extra"
        )
        val categoryProducto3 = CategoriaComplementoEntity(
            3,
            "Crema"
        )
        val categoryProducto4 = CategoriaComplementoEntity(
            4,
            "Ensalada"
        )
        val list=ArrayList<CategoriaComplementoEntity>().apply {
            add(categoryProducto1)
            add(categoryProducto2)
            add(categoryProducto3)
            add(categoryProducto4)
        }

        database.categoryComplementDao().insertManyCategorysComplements(list)

    }

    private fun agregar_complemento(){
        val complementos1= ComplementosEntity(
            1,
            "Papas al hilo Francesas",
            0.0,
            1
        )

        val complementos2= ComplementosEntity(
            2,
            "Papas Fritas",
            5.0,
            1
        )

        val complementos3= ComplementosEntity(
            3,
            "Camote Frito",
            3.00,
            1
        )


        val extras1= ComplementosEntity(
            4,
            "3 Piñas",
            3.50,
            2
        )
        val extras2= ComplementosEntity(
            5,
            "2 Quesos",
            3.00,
            2
        )
        val extras3= ComplementosEntity(
            6,
            "Huevo",
            2.00,
            2
        )
        val extras4= ComplementosEntity(
            7,
            "2 Jamones",
            3.20,
            2
        )
        val extras5= ComplementosEntity(
            8,
            "2 Lomitos",
            3.40,
            2
        )
        val extras6= ComplementosEntity(
            9,
            "4 Tocinos",
            3.00,
            2
        )
        val extras7= ComplementosEntity(
            10,
            "2 Quesos + 3 Piñas",
            4.50,
            2
        )
        val extras8= ComplementosEntity(
            11,
            "2 Quesos + 1 Huevo",
            5.00,
            2
        )
        val extras9= ComplementosEntity(
            12,
            "2 Quesos + 2 Jamones",
            6.00,
            2
        )
        val extras10= ComplementosEntity(
            13,
            "2 Quesos + 2 Lomitos",
            7.00,
            2
        )
        val extras11= ComplementosEntity(
            14,
            "2 Quesos + 4 Tocinos",
            6.50,
            2
        )
        val extras12= ComplementosEntity(
            15,
            "2 Jamones + 1 Huevo",
            4.00,
            2
        )
        val extras13= ComplementosEntity(
            16,
            "2 Jamones + 2 Lomitos",
            4.90,
            2
        )
        val extras14= ComplementosEntity(
            17,
            "2 Jamones + 4 Tocinos",
            8.00,
            2
        )
        val extras15= ComplementosEntity(
            18,
            "2 Lomitos + 1 Huevo",
            9.00,
            2
        )
        val extras16= ComplementosEntity(
            19,
            "2 Lomitos + 4 Tocinos",
            4.00,
            2
        )
        val extras17= ComplementosEntity(
            20,
            "4 Tocinos + 1 Huevo",
            4.00,
            2
        )
        val extras18= ComplementosEntity(
            21,
            "Suprema",
            8.40,
            2
        )
        val extras19= ComplementosEntity(
            22,
            "Milanesa",
            5.50,
            2
        )
        val extras20= ComplementosEntity(
            23,
            "Filete",
            6.70,
            2
        )
        val extras21= ComplementosEntity(
            24,
            "Chuleta",
            5.60,
            2
        )
        val extras22= ComplementosEntity(
            25,
            "Pollo",
            7.40,
            2
        )
        val extras23= ComplementosEntity(
            26,
            "Chorizo",
            5.30,
            2
        )
        val extras24= ComplementosEntity(
            27,
            "Hamburguesa",
            7.30,
            2
        )
        val extras25= ComplementosEntity(
            28,
            "Hot Dog",
            4.80,
            2
        )
        val extras26= ComplementosEntity(
            29,
            "Lechón",
            8.00,
            2
        )
        val extras27= ComplementosEntity(
            30,
            "Pavo",
            8.00,
            2
        )




        val cremas1= ComplementosEntity(
            31,
            "Mayonesa",
            0.0,
            3
        )
        val cremas2= ComplementosEntity(
            32,
            "Tártara",
            0.0,
            3
        )
        val cremas3= ComplementosEntity(
            33,
            "Golf",
            0.0,
            3
        )
        val cremas4= ComplementosEntity(
            34,
            "Palta",
            0.0,
            3
        )
        val cremas5= ComplementosEntity(
            35,
            "Aceituna",
            0.0,
            3
        )
        val cremas6= ComplementosEntity(
            36,
            "Ketchup",
            0.0,
            3
        )
        val cremas7= ComplementosEntity(
            37,
            "Mostaza",
            0.00,
            3
        )
        val cremas8= ComplementosEntity(
            38,
            "Ají",
            0.00,
            3
        )


        val ensaladas1= ComplementosEntity(
            39,
            "Ensalada de Lechuga y Tomate",
            0.00,
            4
        )
        val ensaladas2= ComplementosEntity(
            40,
            "Ensalada Jardinera",
            0.00,
            4
        )
        val ensaladas3= ComplementosEntity(
            41,
            "Ensalada Alemana",
            0.00,
            4
        )


        val list=ArrayList<ComplementosEntity>().apply {
            add(complementos1)
            add(complementos2)
            add(complementos3)
            add(extras1)
            add(extras2)
            add(extras3)
            add(extras4)
            add(extras5)
            add(extras6)

            add(extras7)
            add(extras8)
            add(extras9)
            add(extras10)
            add(extras11)
            add(extras12)
            add(extras13)
            add(extras14)
            add(extras15)
            add(extras16)
            add(extras17)

            add(extras18)
            add(extras19)
            add(extras20)
            add(extras21)
            add(extras22)
            add(extras23)
            add(extras24)
            add(extras25)
            add(extras26)
            add(extras27)


            add(cremas1)
            add(cremas2)
            add(cremas3)
            add(cremas4)
            add(cremas5)
            add(cremas6)
            add(cremas7)
            add(cremas8)

            add(ensaladas1)
            add(ensaladas2)
            add(ensaladas3)
        }
        database.complementDao().insertManyComplements(list)
    }


}