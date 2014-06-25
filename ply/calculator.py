
#Parser for a calculator language with variable assignment

#Token types
tokens=('PLUS','MINUS','TIMES',
        'LPAREN','RPAREN','ASSIGN',
        'IDENTIFIER','LITERAL')

#Regular expresssions
t_PLUS=r'\+'
t_MINUS=r'-'
t_TIMES=r'\*'
t_DIVIDE=r'/'
t_LPAREN=r'\('
t_RPAREN=r'\)'
t_ASSIGN=r'='
t_IDENTIFIER=r'[a-zA-Z_][a-zA-Z0-9_]*'

def t_LITERAL(token):
    r'[0-9]+'
    token.value=int(token.value)
    return token

t_ignore=' '

def t_error(token):
    print "Invalid token: ",token.value
    
#Perform lexical analysis
import ply.lex
ply.lex.lex()


#Operator precedence

precedence=(
    ('left','PLUS','MINUS',),
    ('left','TIMES','DIVIDE')
)

#State
names={}

#Assignment statement

def p_statement_assign(tokens):
    'statement : IDENTIFIER ASSIGN expression'
    names[tokens[1]]= tokens[3]
    
#Print statement

def p_statement_print(tokens):
    'statement : expression'
    print tokens[1]
    
#Arithmetic expression
def p_expression_arithmetic(tokens):
    """ expression: expression PLUS expression
                |    expression MINUS expression
                |    expression TIMES expression
                |    expression DIVIDE expression """
    
    if tokens[2]=='+':
        tokens[0]=tokens[1]+tokens[3]
    elif tokens[2]=='-':
        tokens[0]=tokens[1]-tokens[3]
    elif tokens[2]=='/':
        tokens[0]=tokens[1]/tokens[3]
        
    elif tokens[2]=='*':
        tokens[0]=tokens[1]*tokens[3]
        
#Parenthetical expression

def p_expression_literal(tokens):
    'expression : LPAREN expression RPAREN'
    tokens[0]=tokens[2]
    
#Integer literals
def p_expression_literals(tokens):
    'expression:LITERAL'
    tokens[0]=tokens[1]
    
#Variables
def p_expression_identifier(tokens):
    'expression " IDENTIFIER'
    try:
        tokens[0]=names[tokens[1]]
    except LookupError:
        print "Undefined identifier: ",tokens[1]
        
def p_error(token):
    print "Invalid syntax: ",token.value

#Perform syntax analysis
import ply.yacc
ply.yacc.yacc

while(true):
    line=raw_input('> ')
    ply.yacc.parse(line)
        











