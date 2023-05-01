import ktast.ast.Node

fun kotlinFile(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    packageDirective: Node.PackageDirective? = null,
    importDirectives: Node.ImportDirectives? = null,
    declarations: List<Node.Declaration> = listOf()
) = Node.KotlinFile(
    annotationSets = annotationSets,
    packageDirective = packageDirective,
    importDirectives = importDirectives,
    declarations = declarations
)

fun kotlinScript(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    packageDirective: Node.PackageDirective? = null,
    importDirectives: Node.ImportDirectives? = null,
    expressions: List<Node.Expression> = listOf()
) = Node.KotlinScript(
    annotationSets = annotationSets,
    packageDirective = packageDirective,
    importDirectives = importDirectives,
    expressions = expressions
)

fun packageDirective(
    modifiers: Node.Modifiers? = null,
    packageKeyword: Node.Keyword.Package = Node.Keyword.Package(),
    names: List<Node.Expression.Name> = listOf()
) = Node.PackageDirective(modifiers = modifiers, packageKeyword = packageKeyword, names = names)

fun importDirectives(elements: List<Node.ImportDirective> = listOf()) = Node.ImportDirectives(elements = elements)
fun importDirectives(vararg elements: Node.ImportDirective) = importDirectives(elements.toList())
fun importDirective(
    importKeyword: Node.Keyword.Import = Node.Keyword.Import(),
    names: List<Node.Expression.Name> = listOf(),
    alias: Node.ImportDirective.Alias? = null
) = Node.ImportDirective(importKeyword = importKeyword, names = names, alias = alias)

fun alias(name: Node.Expression.Name) = Node.ImportDirective.Alias(name = name)
fun classDeclaration(
    modifiers: Node.Modifiers? = null,
    declarationKeyword: Node.Declaration.Class.DeclarationKeyword,
    name: Node.Expression.Name? = null,
    typeParams: Node.TypeParams? = null,
    primaryConstructor: Node.Declaration.Class.PrimaryConstructor? = null,
    parents: Node.Declaration.Class.Parents? = null,
    typeConstraints: Node.PostModifier.TypeConstraints? = null,
    body: Node.Declaration.Class.Body? = null
) = Node.Declaration.Class(
    modifiers = modifiers,
    declarationKeyword = declarationKeyword,
    name = name,
    typeParams = typeParams,
    primaryConstructor = primaryConstructor,
    parents = parents,
    typeConstraints = typeConstraints,
    body = body
)

fun declarationKeyword(token: Node.Declaration.Class.DeclarationKeyword.Token) =
    Node.Declaration.Class.DeclarationKeyword(token = token)

fun parents(elements: List<Node.Declaration.Class.Parent> = listOf()) =
    Node.Declaration.Class.Parents(elements = elements)

fun parents(vararg elements: Node.Declaration.Class.Parent) = parents(elements.toList())
fun callConstructorParent(
    type: Node.Type.Simple,
    typeArgs: Node.TypeArgs? = null,
    args: Node.ValueArgs? = null,
    lambda: Node.Expression.Call.LambdaArg? = null
) = Node.Declaration.Class.Parent.CallConstructor(type = type, typeArgs = typeArgs, args = args, lambda = lambda)

fun delegatedTypeParent(
    type: Node.Type.Simple,
    byKeyword: Node.Keyword.By = Node.Keyword.By(),
    expression: Node.Expression
) = Node.Declaration.Class.Parent.DelegatedType(type = type, byKeyword = byKeyword, expression = expression)

fun typeParent(type: Node.Type.Simple) = Node.Declaration.Class.Parent.Type(type = type)
fun primaryConstructor(
    modifiers: Node.Modifiers? = null,
    constructorKeyword: Node.Keyword.Constructor? = null,
    params: Node.Declaration.Function.Params? = null
) = Node.Declaration.Class.PrimaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    params = params
)

fun classBody(
    enumEntries: List<Node.EnumEntry> = listOf(),
    hasTrailingCommaInEnumEntries: Boolean = false,
    declarations: List<Node.Declaration> = listOf()
) = Node.Declaration.Class.Body(
    enumEntries = enumEntries,
    hasTrailingCommaInEnumEntries = hasTrailingCommaInEnumEntries,
    declarations = declarations
)

fun initDeclaration(modifiers: Node.Modifiers? = null, block: Node.Expression.Block) =
    Node.Declaration.Init(modifiers = modifiers, block = block)

fun functionDeclaration(
    modifiers: Node.Modifiers? = null,
    funKeyword: Node.Keyword.Fun = Node.Keyword.Fun(),
    typeParams: Node.TypeParams? = null,
    receiverTypeRef: Node.TypeRef? = null,
    name: Node.Expression.Name? = null,
    params: Node.Declaration.Function.Params? = null,
    typeRef: Node.TypeRef? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    equals: Node.Keyword.Equal? = null,
    body: Node.Expression? = null
) = Node.Declaration.Function(
    modifiers = modifiers,
    funKeyword = funKeyword,
    typeParams = typeParams,
    receiverTypeRef = receiverTypeRef,
    name = name,
    params = params,
    typeRef = typeRef,
    postModifiers = postModifiers,
    equals = if (equals == null && body != null && body !is Node.Expression.Block) Node.Keyword.Equal() else equals,
    body = body
)

fun functionParams(
    elements: List<Node.Declaration.Function.Param> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.Declaration.Function.Params(elements = elements, trailingComma = trailingComma)

fun functionParams(vararg elements: Node.Declaration.Function.Param) = functionParams(elements.toList())
fun functionParam(
    modifiers: Node.Modifiers? = null,
    valOrVar: Node.Declaration.Property.ValOrVar? = null,
    name: Node.Expression.Name,
    typeRef: Node.TypeRef? = null,
    equals: Node.Keyword.Equal? = null,
    defaultValue: Node.Expression? = null
) = Node.Declaration.Function.Param(
    modifiers = modifiers,
    valOrVar = valOrVar,
    name = name,
    typeRef = typeRef,
    equals = if (equals == null && defaultValue != null) Node.Keyword.Equal() else equals,
    defaultValue = defaultValue
)

fun propertyDeclaration(
    modifiers: Node.Modifiers? = null,
    valOrVar: Node.Declaration.Property.ValOrVar,
    typeParams: Node.TypeParams? = null,
    receiverTypeRef: Node.TypeRef? = null,
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Declaration.Property.Variable> = listOf(),
    trailingComma: Node.Keyword.Comma? = null,
    rPar: Node.Keyword.RPar? = null,
    typeConstraints: Node.PostModifier.TypeConstraints? = null,
    equals: Node.Keyword.Equal? = null,
    initializer: Node.Expression? = null,
    delegate: Node.Declaration.Property.Delegate? = null,
    accessors: List<Node.Declaration.Property.Accessor> = listOf()
) = Node.Declaration.Property(
    modifiers = modifiers,
    valOrVar = valOrVar,
    typeParams = typeParams,
    receiverTypeRef = receiverTypeRef,
    lPar = lPar,
    variables = variables,
    trailingComma = trailingComma,
    rPar = rPar,
    typeConstraints = typeConstraints,
    equals = if (equals == null && initializer != null) Node.Keyword.Equal() else equals,
    initializer = initializer,
    delegate = delegate,
    accessors = accessors
)

fun valOrVar(token: Node.Declaration.Property.ValOrVar.Token) = Node.Declaration.Property.ValOrVar(token = token)
fun variable(name: Node.Expression.Name, typeRef: Node.TypeRef? = null) =
    Node.Declaration.Property.Variable(name = name, typeRef = typeRef)

fun delegate(byKeyword: Node.Keyword.By = Node.Keyword.By(), expression: Node.Expression) =
    Node.Declaration.Property.Delegate(byKeyword = byKeyword, expression = expression)

fun getter(
    modifiers: Node.Modifiers? = null,
    getKeyword: Node.Keyword.Get = Node.Keyword.Get(),
    typeRef: Node.TypeRef? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    equals: Node.Keyword.Equal? = null,
    body: Node.Expression? = null
) = Node.Declaration.Property.Accessor.Getter(
    modifiers = modifiers,
    getKeyword = getKeyword,
    typeRef = typeRef,
    postModifiers = postModifiers,
    equals = if (equals == null && body != null && body !is Node.Expression.Block) Node.Keyword.Equal() else equals,
    body = body
)

fun setter(
    modifiers: Node.Modifiers? = null,
    setKeyword: Node.Keyword.Set = Node.Keyword.Set(),
    params: Node.Expression.Lambda.Params? = null,
    postModifiers: List<Node.PostModifier> = listOf(),
    equals: Node.Keyword.Equal? = null,
    body: Node.Expression? = null
) = Node.Declaration.Property.Accessor.Setter(
    modifiers = modifiers,
    setKeyword = setKeyword,
    params = params,
    postModifiers = postModifiers,
    equals = if (equals == null && body != null && body !is Node.Expression.Block) Node.Keyword.Equal() else equals,
    body = body
)

fun typeAliasDeclaration(
    modifiers: Node.Modifiers? = null,
    name: Node.Expression.Name,
    typeParams: Node.TypeParams? = null,
    typeRef: Node.TypeRef
) = Node.Declaration.TypeAlias(modifiers = modifiers, name = name, typeParams = typeParams, typeRef = typeRef)

fun secondaryConstructorDeclaration(
    modifiers: Node.Modifiers? = null,
    constructorKeyword: Node.Keyword.Constructor = Node.Keyword.Constructor(),
    params: Node.Declaration.Function.Params? = null,
    delegationCall: Node.Declaration.SecondaryConstructor.DelegationCall? = null,
    block: Node.Expression.Block? = null
) = Node.Declaration.SecondaryConstructor(
    modifiers = modifiers,
    constructorKeyword = constructorKeyword,
    params = params,
    delegationCall = delegationCall,
    block = block
)

fun delegationCall(target: Node.Declaration.SecondaryConstructor.DelegationTarget, args: Node.ValueArgs? = null) =
    Node.Declaration.SecondaryConstructor.DelegationCall(target = target, args = args)

fun delegationTarget(token: Node.Declaration.SecondaryConstructor.DelegationTarget.Token) =
    Node.Declaration.SecondaryConstructor.DelegationTarget(token = token)

fun enumEntry(
    modifiers: Node.Modifiers? = null,
    name: Node.Expression.Name,
    args: Node.ValueArgs? = null,
    body: Node.Declaration.Class.Body? = null
) = Node.EnumEntry(modifiers = modifiers, name = name, args = args, body = body)

fun typeParams(elements: List<Node.TypeParam> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.TypeParams(elements = elements, trailingComma = trailingComma)

fun typeParams(vararg elements: Node.TypeParam) = typeParams(elements.toList())
fun typeParam(modifiers: Node.Modifiers? = null, name: Node.Expression.Name, typeRef: Node.TypeRef? = null) =
    Node.TypeParam(modifiers = modifiers, name = name, typeRef = typeRef)

fun functionType(
    lPar: Node.Keyword.LPar? = null,
    modifiers: Node.Modifiers? = null,
    contextReceivers: Node.Type.Function.ContextReceivers? = null,
    receiver: Node.Type.Function.Receiver? = null,
    params: Node.Type.Function.Params? = null,
    returnTypeRef: Node.TypeRef,
    rPar: Node.Keyword.RPar? = null
) = Node.Type.Function(
    lPar = lPar,
    modifiers = modifiers,
    contextReceivers = contextReceivers,
    receiver = receiver,
    params = params,
    returnTypeRef = returnTypeRef,
    rPar = rPar
)

fun contextReceivers(
    elements: List<Node.Type.Function.ContextReceiver> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.Type.Function.ContextReceivers(elements = elements, trailingComma = trailingComma)

fun contextReceivers(vararg elements: Node.Type.Function.ContextReceiver) = contextReceivers(elements.toList())
fun contextReceiver(typeRef: Node.TypeRef) = Node.Type.Function.ContextReceiver(typeRef = typeRef)
fun functionTypeReceiver(typeRef: Node.TypeRef) = Node.Type.Function.Receiver(typeRef = typeRef)
fun functionTypeParams(elements: List<Node.Type.Function.Param> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Type.Function.Params(elements = elements, trailingComma = trailingComma)

fun functionTypeParams(vararg elements: Node.Type.Function.Param) = functionTypeParams(elements.toList())
fun functionTypeParam(name: Node.Expression.Name? = null, typeRef: Node.TypeRef) =
    Node.Type.Function.Param(name = name, typeRef = typeRef)

fun simpleType(
    qualifiers: List<Node.Type.Simple.Qualifier> = listOf(),
    name: Node.Expression.Name,
    typeArgs: Node.TypeArgs? = null
) = Node.Type.Simple(qualifiers = qualifiers, name = name, typeArgs = typeArgs)

fun qualifier(name: Node.Expression.Name, typeArgs: Node.TypeArgs? = null) =
    Node.Type.Simple.Qualifier(name = name, typeArgs = typeArgs)

fun nullableType(
    lPar: Node.Keyword.LPar? = null,
    modifiers: Node.Modifiers? = null,
    type: Node.Type,
    rPar: Node.Keyword.RPar? = null
) = Node.Type.Nullable(lPar = lPar, modifiers = modifiers, type = type, rPar = rPar)

fun dynamicType(_unused_: Boolean = false) = Node.Type.Dynamic(_unused_ = _unused_)
fun typeArgs(elements: List<Node.TypeArg> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.TypeArgs(elements = elements, trailingComma = trailingComma)

fun typeArgs(vararg elements: Node.TypeArg) = typeArgs(elements.toList())
fun typeArg(modifiers: Node.Modifiers? = null, typeRef: Node.TypeRef? = null, asterisk: Boolean = false) =
    Node.TypeArg(modifiers = modifiers, typeRef = typeRef, asterisk = asterisk)

fun typeRef(
    lPar: Node.Keyword.LPar? = null,
    modifiers: Node.Modifiers? = null,
    type: Node.Type,
    rPar: Node.Keyword.RPar? = null
) = Node.TypeRef(lPar = lPar, modifiers = modifiers, type = type, rPar = rPar)

fun valueArgs(elements: List<Node.ValueArg> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.ValueArgs(elements = elements, trailingComma = trailingComma)

fun valueArgs(vararg elements: Node.ValueArg) = valueArgs(elements.toList())
fun valueArg(name: Node.Expression.Name? = null, asterisk: Boolean = false, expression: Node.Expression) =
    Node.ValueArg(name = name, asterisk = asterisk, expression = expression)

fun expressionContainer(expression: Node.Expression) = Node.ExpressionContainer(expression = expression)
fun ifExpression(
    ifKeyword: Node.Keyword.If = Node.Keyword.If(),
    condition: Node.Expression,
    body: Node.ExpressionContainer,
    elseBody: Node.ExpressionContainer? = null
) = Node.Expression.If(ifKeyword = ifKeyword, condition = condition, body = body, elseBody = elseBody)

fun tryExpression(
    block: Node.Expression.Block,
    catches: List<Node.Expression.Try.Catch> = listOf(),
    finallyBlock: Node.Expression.Block? = null
) = Node.Expression.Try(block = block, catches = catches, finallyBlock = finallyBlock)

fun catch(
    catchKeyword: Node.Keyword.Catch = Node.Keyword.Catch(),
    params: Node.Declaration.Function.Params,
    block: Node.Expression.Block
) = Node.Expression.Try.Catch(catchKeyword = catchKeyword, params = params, block = block)

fun forExpression(
    forKeyword: Node.Keyword.For = Node.Keyword.For(),
    loopParam: Node.Expression.Lambda.Param,
    loopRange: Node.ExpressionContainer,
    body: Node.ExpressionContainer
) = Node.Expression.For(forKeyword = forKeyword, loopParam = loopParam, loopRange = loopRange, body = body)

fun whileExpression(
    whileKeyword: Node.Keyword.While = Node.Keyword.While(),
    condition: Node.ExpressionContainer,
    body: Node.ExpressionContainer,
    doWhile: Boolean = false
) = Node.Expression.While(whileKeyword = whileKeyword, condition = condition, body = body, doWhile = doWhile)

fun binaryExpression(lhs: Node.Expression, operator: Node.Expression.Binary.Operator, rhs: Node.Expression) =
    Node.Expression.Binary(lhs = lhs, operator = operator, rhs = rhs)

fun binaryOperator(token: Node.Expression.Binary.Operator.Token) = Node.Expression.Binary.Operator(token = token)
fun binaryInfixExpression(lhs: Node.Expression, operator: Node.Expression.Name, rhs: Node.Expression) =
    Node.Expression.BinaryInfix(lhs = lhs, operator = operator, rhs = rhs)

fun unaryExpression(expression: Node.Expression, operator: Node.Expression.Unary.Operator, prefix: Boolean = false) =
    Node.Expression.Unary(expression = expression, operator = operator, prefix = prefix)

fun unaryOperator(token: Node.Expression.Unary.Operator.Token) = Node.Expression.Unary.Operator(token = token)
fun binaryTypeExpression(lhs: Node.Expression, operator: Node.Expression.BinaryType.Operator, rhs: Node.TypeRef) =
    Node.Expression.BinaryType(lhs = lhs, operator = operator, rhs = rhs)

fun binaryTypeOperator(token: Node.Expression.BinaryType.Operator.Token) =
    Node.Expression.BinaryType.Operator(token = token)

fun expressionDoubleColonReceiver(expression: Node.Expression) =
    Node.Expression.DoubleColon.Receiver.Expression(expression = expression)

fun typeDoubleColonReceiver(type: Node.Type.Simple, questionMarks: List<Node.Keyword.Question> = listOf()) =
    Node.Expression.DoubleColon.Receiver.Type(type = type, questionMarks = questionMarks)

fun callableReferenceExpression(lhs: Node.Expression.DoubleColon.Receiver? = null, rhs: Node.Expression.Name) =
    Node.Expression.CallableReference(lhs = lhs, rhs = rhs)

fun classLiteralExpression(lhs: Node.Expression.DoubleColon.Receiver? = null) = Node.Expression.ClassLiteral(lhs = lhs)
fun parenthesizedExpression(expression: Node.Expression) = Node.Expression.Parenthesized(expression = expression)
fun stringTemplateExpression(entries: List<Node.Expression.StringTemplate.Entry> = listOf(), raw: Boolean = false) =
    Node.Expression.StringTemplate(entries = entries, raw = raw)

fun regular(str: String) = Node.Expression.StringTemplate.Entry.Regular(str = str)
fun shortTemplate(str: String) = Node.Expression.StringTemplate.Entry.ShortTemplate(str = str)
fun unicodeEscape(digits: String) = Node.Expression.StringTemplate.Entry.UnicodeEscape(digits = digits)
fun regularEscape(char: Char) = Node.Expression.StringTemplate.Entry.RegularEscape(char = char)
fun longTemplate(expression: Node.Expression) =
    Node.Expression.StringTemplate.Entry.LongTemplate(expression = expression)

fun constantExpression(value: String, form: Node.Expression.Constant.Form) =
    Node.Expression.Constant(value = value, form = form)

fun lambdaExpression(params: Node.Expression.Lambda.Params? = null, body: Node.Expression.Lambda.Body? = null) =
    Node.Expression.Lambda(params = params, body = body)

fun lambdaParams(elements: List<Node.Expression.Lambda.Param> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Expression.Lambda.Params(elements = elements, trailingComma = trailingComma)

fun lambdaParams(vararg elements: Node.Expression.Lambda.Param) = lambdaParams(elements.toList())
fun lambdaParam(
    lPar: Node.Keyword.LPar? = null,
    variables: List<Node.Expression.Lambda.Param.Variable> = listOf(),
    trailingComma: Node.Keyword.Comma? = null,
    rPar: Node.Keyword.RPar? = null,
    colon: Node.Keyword.Colon? = null,
    destructTypeRef: Node.TypeRef? = null
) = Node.Expression.Lambda.Param(
    lPar = lPar,
    variables = variables,
    trailingComma = trailingComma,
    rPar = rPar,
    colon = colon,
    destructTypeRef = destructTypeRef
)

fun variable(modifiers: Node.Modifiers? = null, name: Node.Expression.Name, typeRef: Node.TypeRef? = null) =
    Node.Expression.Lambda.Param.Variable(modifiers = modifiers, name = name, typeRef = typeRef)

fun lambdaBody(statements: List<Node.Statement> = listOf()) = Node.Expression.Lambda.Body(statements = statements)
fun lambdaBody(vararg statements: Node.Statement) = lambdaBody(statements.toList())
fun thisExpression(label: String? = null) = Node.Expression.This(label = label)
fun superExpression(typeArg: Node.TypeRef? = null, label: String? = null) =
    Node.Expression.Super(typeArg = typeArg, label = label)

fun whenExpression(
    whenKeyword: Node.Keyword.When = Node.Keyword.When(),
    lPar: Node.Keyword.LPar? = null,
    expression: Node.Expression? = null,
    rPar: Node.Keyword.RPar? = null,
    branches: List<Node.Expression.When.Branch> = listOf()
) = Node.Expression.When(
    whenKeyword = whenKeyword,
    lPar = lPar,
    expression = expression,
    rPar = rPar,
    branches = branches
)

fun conditionalBranch(
    conditions: List<Node.Expression.When.Condition> = listOf(),
    trailingComma: Node.Keyword.Comma? = null,
    body: Node.Expression
) = Node.Expression.When.Branch.Conditional(conditions = conditions, trailingComma = trailingComma, body = body)

fun elseBranch(elseKeyword: Node.Keyword.Else = Node.Keyword.Else(), body: Node.Expression) =
    Node.Expression.When.Branch.Else(elseKeyword = elseKeyword, body = body)

fun expressionCondition(expression: Node.Expression) =
    Node.Expression.When.Condition.Expression(expression = expression)

fun inCondition(expression: Node.Expression, not: Boolean = false) =
    Node.Expression.When.Condition.In(expression = expression, not = not)

fun isCondition(typeRef: Node.TypeRef, not: Boolean = false) =
    Node.Expression.When.Condition.Is(typeRef = typeRef, not = not)

fun objectExpression(declaration: Node.Declaration.Class) = Node.Expression.Object(declaration = declaration)
fun throwExpression(expression: Node.Expression) = Node.Expression.Throw(expression = expression)
fun returnExpression(label: String? = null, expression: Node.Expression? = null) =
    Node.Expression.Return(label = label, expression = expression)

fun continueExpression(label: String? = null) = Node.Expression.Continue(label = label)
fun breakExpression(label: String? = null) = Node.Expression.Break(label = label)
fun collectionLiteralExpression(
    expressions: List<Node.Expression> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.Expression.CollectionLiteral(expressions = expressions, trailingComma = trailingComma)

fun nameExpression(name: String) = Node.Expression.Name(name = name)
fun labeledExpression(label: String, expression: Node.Expression) =
    Node.Expression.Labeled(label = label, expression = expression)

fun annotatedExpression(annotationSets: List<Node.Modifier.AnnotationSet> = listOf(), expression: Node.Expression) =
    Node.Expression.Annotated(annotationSets = annotationSets, expression = expression)

fun callExpression(
    expression: Node.Expression,
    typeArgs: Node.TypeArgs? = null,
    args: Node.ValueArgs? = null,
    lambdaArg: Node.Expression.Call.LambdaArg? = null
) = Node.Expression.Call(expression = expression, typeArgs = typeArgs, args = args, lambdaArg = lambdaArg)

fun lambdaArg(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    label: String? = null,
    expression: Node.Expression.Lambda
) = Node.Expression.Call.LambdaArg(annotationSets = annotationSets, label = label, expression = expression)

fun arrayAccessExpression(
    expression: Node.Expression,
    indices: List<Node.Expression> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.Expression.ArrayAccess(expression = expression, indices = indices, trailingComma = trailingComma)

fun anonymousFunctionExpression(function: Node.Declaration.Function) =
    Node.Expression.AnonymousFunction(function = function)

fun propertyExpression(declaration: Node.Declaration.Property) = Node.Expression.Property(declaration = declaration)
fun blockExpression(statements: List<Node.Statement> = listOf()) = Node.Expression.Block(statements = statements)
fun blockExpression(vararg statements: Node.Statement) = blockExpression(statements.toList())
fun modifiers(elements: List<Node.Modifier> = listOf()) = Node.Modifiers(elements = elements)
fun modifiers(vararg elements: Node.Modifier) = modifiers(elements.toList())
fun annotationSet(
    atSymbol: Node.Keyword.At? = null,
    target: Node.Modifier.AnnotationSet.Target? = null,
    colon: Node.Keyword.Colon? = null,
    lBracket: Node.Keyword.LBracket? = null,
    annotations: List<Node.Modifier.AnnotationSet.Annotation> = listOf(),
    rBracket: Node.Keyword.RBracket? = null
) = Node.Modifier.AnnotationSet(
    atSymbol = atSymbol,
    target = target,
    colon = colon,
    lBracket = lBracket,
    annotations = annotations,
    rBracket = rBracket
)

fun annotationSetTarget(token: Node.Modifier.AnnotationSet.Target.Token) =
    Node.Modifier.AnnotationSet.Target(token = token)

fun annotation(type: Node.Type.Simple, args: Node.ValueArgs? = null) =
    Node.Modifier.AnnotationSet.Annotation(type = type, args = args)

fun keywordModifier(token: Node.Modifier.Keyword.Token) = Node.Modifier.Keyword(token = token)
fun typeConstraints(
    whereKeyword: Node.Keyword.Where = Node.Keyword.Where(),
    constraints: Node.PostModifier.TypeConstraints.TypeConstraintList
) = Node.PostModifier.TypeConstraints(whereKeyword = whereKeyword, constraints = constraints)

fun typeConstraintList(elements: List<Node.PostModifier.TypeConstraints.TypeConstraint> = listOf()) =
    Node.PostModifier.TypeConstraints.TypeConstraintList(elements = elements)

fun typeConstraintList(vararg elements: Node.PostModifier.TypeConstraints.TypeConstraint) =
    typeConstraintList(elements.toList())

fun typeConstraint(
    annotationSets: List<Node.Modifier.AnnotationSet> = listOf(),
    name: Node.Expression.Name,
    typeRef: Node.TypeRef
) = Node.PostModifier.TypeConstraints.TypeConstraint(annotationSets = annotationSets, name = name, typeRef = typeRef)

fun contract(
    contractKeyword: Node.Keyword.Contract = Node.Keyword.Contract(),
    contractEffects: Node.PostModifier.Contract.ContractEffects
) = Node.PostModifier.Contract(contractKeyword = contractKeyword, contractEffects = contractEffects)

fun contractEffects(
    elements: List<Node.PostModifier.Contract.ContractEffect> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.PostModifier.Contract.ContractEffects(elements = elements, trailingComma = trailingComma)

fun contractEffects(vararg elements: Node.PostModifier.Contract.ContractEffect) = contractEffects(elements.toList())
fun contractEffect(expression: Node.Expression) = Node.PostModifier.Contract.ContractEffect(expression = expression)
fun whitespace(text: String) = Node.Extra.Whitespace(text = text)
fun comment(text: String) = Node.Extra.Comment(text = text)
fun semicolon(text: String) = Node.Extra.Semicolon(text = text)
