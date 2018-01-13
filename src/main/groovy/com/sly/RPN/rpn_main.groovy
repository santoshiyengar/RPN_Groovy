import RPNClass

RPNClass p = new RPNClass()

while(true) {
    String inputText = p.getInput()

    if(p.isExpression(inputText)){
        try {
            String output = p.evaluateExpression(inputText)
            output?println(output):""
        }
        catch (Exception ex){
            p.rpnHelp()
            continue
        }
    }
}