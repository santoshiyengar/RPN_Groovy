class RPNClass{

    def errorFlag = false

    void rpnHelp(){
        errorFlag ?:println(new File('/home/sly/Documents/git/RPN_Groovy/src/com/sly/RPN/help.txt').text)
        errorFlag = true
    }

    Boolean isExpression(String inputText){
        if(inputText == '') {
            return false
        }
        else if(inputText in ['h','help','Help','HELP']) {
            rpnHelp()
        }
        else if(inputText in ['q','quit','exit']) {
            System.exit(0)
        }
        else {
            return true
        }
    }

    String getInput(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        print ">>> "
        return br.readLine()
    }

    String evaluateExpression(String inputExpression) {
        errorFlag = false
        def stack = [] as Stack
        def binaryOp = {action -> return {action.call(stack.pop(), stack.pop())}}
        def unaryOp= {action -> return {action.call(stack.pop())}}

        def binaryActions = [
                '+': binaryOp{ a, b -> b + a},
                '-': binaryOp{ a, b -> b - a},
                '*': binaryOp{ a, b -> b * a},
                '/': binaryOp{ a, b -> b / a},
                '#': binaryOp{ a, b -> b % a},
                '^': binaryOp{ a, b -> b ** a}

        ]

        def unaryActions = [
                '!': unaryOp{ a -> !a},
                '%': unaryOp{ a -> a/100}
        ]

        inputExpression.split(' ').each { token ->
            def action = binaryActions[token]?:unaryActions[token]?:RPNMathClass.isValidNumber(token)?{token as BigDecimal}:this.rpnHelp()
            stack.push(action.call())
        }
        stack.size() == 1?stack.pop():this.rpnHelp()
    }
}
