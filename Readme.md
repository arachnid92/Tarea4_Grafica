    BattleSpace is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    BattleSpace is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
  
    You should have received a copy of the GNU General Public License
    along with BattleSpace.  If not, see <http://www.gnu.org/licenses/>.



    BattleSpace es software libre: puede ser redistribuído y/o modificado
    bajo los t�rminos de la GNU General Public License, publicada por la
    Free Software Foundation, ya sea la versi�n 3 de la Licencia, o, (a
    discreci�n) cualquier versi�n posterior.

    BattleSpace es distribu�da con la esperanza de que sea �til, pero 
    SIN GARANT��A ALGUNA; ni siquiera la garant�a impl�cita de MERCANTA-
    BILIDAD o APTITUD PARA UN FIN ESPEC�FICO. Para m�s detalles, ver la
    GNU General Public License.

    Debe haber recibido una copia de la GNU General Public License junto
    con BattleSpace. De no ser as�, vea <http://www.gnu.org/licenses/>.


.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.

Autor: Manuel Olguín

BattleSpace es un juego escrito en Java, implementando las librer�as Slick2D
y LWJGL. Fue creado para una tarea de un curso de Computaci�n Gr�fica de la 
Universidad de Chile.

Para compilar el juego:

1. Se debe tener instalada la versi�n 1.6 o 1.7 del Java Development Kit, ya sea la versi�n de Oracle u OpenJDK.
2. En Eclipse, es cosa de ir a File -> Import -> Existing Proyects Into Workspace -> Buscamos la carpeta que contiene todos los archivos, seleccionamos "Juego_Graficas" y le ponemos OK.
3. Si al abrirlo nos tira error es porque tenemos mal configurado el JDK. Hacemos click derecho en el proyecto "Juego_Graficas" -> Propierties.
4. Vamos a "Java Build Path" -> Libraries. Seleccionamos la libreria que diga algo as� como "JRE System Library [JavaSE-1.7] (unbound)" y hacemos click en eliminar.
5. Luego hacemos click en Add Library -> JRE System Library -> Workspace Default... -> Finish.
	5a. Adem�s, si estamos en Windows, debemos volver a Java Build Path -> Libraries. 
	5b. Hacemos click en la flecha a la izquierda de "lwjgl.jar".
	5c. Doble click en "Native library location: Juego_Graficas..."
	5d. En la ventana que se abre, seleccionamos el bot�n "workspace".
	5e. Navegamos a Juego_Graficas -> lib -> Windows y presionamos OK.
6. Luego, ejecutamos el proyecto. Si Eclipse muestra una ventana preguntando que clase ejecutar, buscamos ShipGame y hacemos doble click.
7. ???
8. Profit!!!


Tambi�n se adjuntan ejecutables para Windows y Linux.


INSTRUCCIONES DE JUEGO:

Es un juego de dos jugadores. La finalidad del juego es, a trav�s de el arma
propia de cada nave, destruir la nave enemiga; el problema es que las naves
son invulnerables por delante, y la �nica manera de causar da�o a la nave 
enemiga es dispar�ndole en la parte posterior.

Cada nave tambi�n tiene asociado un contador de combustible. Si este llega a 
cero, la nave no podr� seguir movi�ndose. En el mapa aparecer�n oportunamente
contenedores de combustible para rellenar los estanques de las naves. Tambi�n
apareceran vidas adicionales y powerups para aumentar la potencia del arma
de cada nave.

En el mapa tambi�n existen asteroides que da�an las naves al chocar con ellas.

CONTROLES:

FLECHA ARRIBA:	Rojo acelera.
FLECHA ABAJO:		Rojo retrocede.
FLECHA IZQ:		Rojo gira izquierda.
FLECHA DER:		Rojo gira derecha.
CONTROL DER:		Rojo dispara.

W:				Azul acelera.
S:				Azul retrocede.
A:				Azul gira izquierda.
D.				Azul gira derecha.
CONTROL IZQ:		Azul dispara.

P:				Pausa	 

