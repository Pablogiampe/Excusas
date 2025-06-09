# Parcial Giampetruzzi - Sistema de Gestión de Excusas

## Descripción
Sistema para la empresa ficticia "Excusas S.A." que gestiona las excusas de empleados que llegan tarde mediante una cadena de encargados especializados.

## Patrones de Diseño Implementados

### 1. Chain of Responsibility (Cadena de Responsabilidad)
- **Propósito**: Permite que múltiples objetos tengan la oportunidad de manejar una solicitud sin acoplar el emisor con los receptores.
- **Implementación**: 
  - `EncargadoBase` como handler abstracto
  - `Recepcionista`, `SupervisorArea`, `GerenteRRHH`, `CEO` como handlers concretos
  - `Rechazador` como handler por defecto
- **Justificación**: Cada tipo de excusa debe ser evaluada por un encargado específico, y si no puede procesarla, la pasa al siguiente en la línea.

### 2. Observer (Observador)
- **Propósito**: Define una dependencia uno-a-muchos entre objetos para que cuando un objeto cambie de estado, todos sus dependientes sean notificados.
- **Implementación**:
  - `Observable` e `Observer` como interfaces
  - `AdministradorProntuarios` como sujeto observable
  - `CEO` como observador
- **Justificación**: Cuando se crea un nuevo prontuario, todos los CEOs deben ser notificados.

### 3. Strategy (Estrategia)
- **Propósito**: Define una familia de algoritmos, los encapsula y los hace intercambiables.
- **Implementación**:
  - `ModoResolucion` como interfaz de estrategia
  - `ModoNormal`, `ModoVago`, `ModoProductivo` como estrategias concretas
- **Justificación**: Cada encargado puede tener diferentes formas de resolver las excusas.

### 4. Singleton
- **Propósito**: Garantiza que una clase tenga una sola instancia y proporciona un punto de acceso global.
- **Implementación**: `AdministradorProntuarios`
- **Justificación**: Solo debe existir un administrador de prontuarios para toda la empresa.

## Estructura del Proyecto

\`\`\`
src/
├── main/java/com/excusas/
│   ├── empleados/
│   │   └── Empleado.java
│   ├── encargados/
│   │   ├── estrategias/
│   │   │   ├── ModoResolucion.java
│   │   │   ├── ModoNormal.java
│   │   │   ├── ModoVago.java
│   │   │   ├── ModoProductivo.java
│   │   │   └── EncargadoBase.java
│   │   ├── ManejadorExcusas.java
│   │   ├── Recepcionista.java
│   │   ├── SupervisorArea.java
│   │   ├── GerenteRRHH.java
│   │   ├── CEO.java
│   │   ├── Rechazador.java
│   │   └── LineaEncargados.java
│   ├── excusas/
│   │   ├── MotivoExcusa.java
│   │   ├── Excusa.java
│   │   ├── triviales/ExcusaTrivial.java
│   │   ├── moderadas/ExcusaModerada.java
│   │   ├── complejas/ExcusaCompleja.java
│   │   └── inverosimiles/ExcusaInverosimil.java
│   ├── mail/
│   │   ├── EmailSender.java
│   │   └── EmailSenderImpl.java
│   ├── prontuario/
│   │   ├── Prontuario.java
│   │   ├── Observer.java
│   │   ├── Observable.java
│   │   └── AdministradorProntuarios.java
│   ├── excepciones/
│   │   ├── ExcusaSAException.java
│   │   ├── ErrorConfiguracion.java
│   │   ├── ErrorProcesamiento.java
│   │   └── TipoError.java
│   └── Main.java
└── test/java/com/excusas/
    ├── encargados/
    │   ├── RecepcionistaTest.java
    │   ├── CEOTest.java
    │   └── LineaEncargadosTest.java
    ├── prontuario/
    │   └── AdministradorProntuariosTest.java
    └── excusas/
        └── ExcusaTest.java
\`\`\`

## Principios SOLID Aplicados

1. **Single Responsibility Principle (SRP)**: Cada clase tiene una única responsabilidad.
2. **Open/Closed Principle (OCP)**: El sistema está abierto para extensión (nuevos encargados) pero cerrado para modificación.
3. **Liskov Substitution Principle (LSP)**: Las subclases pueden sustituir a sus clases base.
4. **Interface Segregation Principle (ISP)**: Interfaces específicas en lugar de una interfaz general.
5. **Dependency Inversion Principle (DIP)**: Dependencia de abstracciones, no de concreciones.

## Ejecución

### Compilar y ejecutar
\`\`\`bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.excusas.Main"
\`\`\`

### Ejecutar tests
\`\`\`bash
mvn test
\`\`\`

### Generar JAR
\`\`\`bash
mvn clean package
\`\`\`

## Casos de Uso

1. **Procesar Excusa Trivial**: El recepcionista maneja excusas como "me quedé dormido".
2. **Procesar Excusa Moderada**: El supervisor maneja excusas como "se cortó la luz".
3. **Procesar Excusa Compleja**: El gerente de RRHH maneja excusas complejas.
4. **Procesar Excusa Inverosímil**: El CEO maneja excusas extremadamente inverosímiles.
5. **Rechazar Excusa**: Si ningún encargado puede manejar la excusa, se rechaza.
6. **Notificar Prontuario**: Cuando se crea un prontuario, se notifica a todos los CEOs.

## Correcciones Implementadas

- ✅ Estructura de repositorio corregida
- ✅ Módulo de encargados dentro del módulo de empleados
- ✅ Administrador de prontuarios con comportamiento correcto
- ✅ Empleado no genera excusas directamente
- ✅ Clase que encapsula la cadena de encargados
- ✅ Lógica del template method implementada correctamente
- ✅ Balance adecuado de responsabilidades
- ✅ Dispatching correcto entre abstracciones
- ✅ EmailSender no compartido entre encargados
- ✅ Estrategias en módulo separado
- ✅ Excepciones personalizadas implementadas
- ✅ Subtipos de excusas con comportamiento específico
- ✅ Clase base para administrador de prontuarios con lógica observable
- ✅ Tests unitarios con Mockito
