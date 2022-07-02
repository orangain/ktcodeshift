import ktast.ast.Node

fun file(
    anns: List<Node.Modifier.AnnotationSet> = listOf(),
    pkg: Node.Package? = null,
    imports: Node.Imports? = null,
    decls: List<Node.Decl> = listOf()
) = Node.File(anns = anns, pkg = pkg, imports = imports, decls = decls)

fun script(
    anns: List<Node.Modifier.AnnotationSet> = listOf(),
    pkg: Node.Package? = null,
    imports: Node.Imports? = null,
    exprs: List<Node.Expr> = listOf()
) = Node.Script(anns = anns, pkg = pkg, imports = imports, exprs = exprs)

fun packageDirective(
    mods: Node.Modifiers? = null,
    packageKeyword: Node.Keyword.Package = Node.Keyword.Package(),
    names: List<Node.Expr.Name> = listOf()
) = Node.Package(mods = mods, packageKeyword = packageKeyword, names = names)

fun importDirectives(elements: List<Node.Import> = listOf()) = Node.Imports(elements = elements)
fun importDirectives(vararg elements: Node.Import) = importDirectives(elements.toList())
fun importDirective(
    importKeyword: Node.Keyword.Import = Node.Keyword.Import(),
    names: List<Node.Expr.Name> = listOf(),
    alias: Node.Import.Alias? = null
) = Node.Import(importKeyword = importKeyword, names = names, alias = alias)

fun alias(name: Node.Expr.Name) = Node.Import.Alias(name = name)
fun structured(
    mods: Node.Modifiers? = null,
    declarationKeyword: Node.Keyword.Declaration,
    name: Node.Expr.Name? = null,
    typeParams: Node.TypeParams? = null,
    primaryConstructor: Node.Decl.Structured.PrimaryConstructor? = null,
    parents: Node.Decl.Structured.Parents? = null,
    typeConstraints: Node.PostModifier.TypeConstraints? = null,
    body: Node.Decl.Structured.Body? = null
) = Node.Decl.Structured(
    mods = mods,
    declarationKeyword = declarationKeyword,
    name = name,
    typeParams = typeParams,
    primaryConstructor = primaryConstructor,
    parents = parents,
    typeConstraints = typeConstraints,
    body = body
)

fun parents(items: List<Node.Decl.Structured.Parent> = listOf()) = Node.Decl.Structured.Parents(items = items)
fun callConstructor(
    type: Node.Type.Simple,
    typeArgs: Node.TypeArgs? = null,
    args: Node.ValueArgs? = null,
    lambda: Node.Expr.Call.LambdaArg? = null
) = Node.Decl.Structured.Parent.CallConstructor(type = type, typeArgs = typeArgs, args = args, lambda = lambda)

fun delegatedType(type: Node.Type.Simple, byKeyword: Node.Keyword.By = Node.Keyword.By(), expr: Node.Expr) =
    Node.Decl.Structured.Parent.DelegatedType(type = type, byKeyword = byKeyword, expr = expr)

fun type(type: Node.Type.Simple) = Node.Decl.Structured.Parent.Type(type = type)
fun primaryConstructor(
    mods: Node.Modifiers? = null,
    constructorKeyword: Node.Keyword.Constructor? = null,
    params: Node.Decl.Func.Params? = null
) = Node.Decl.Structured.PrimaryConstructor(mods = mods, constructorKeyword = constructorKeyword, params = params)

fun body(
    enumEntries: List<Node.EnumEntry> = listOf(),
    hasTrailingCommaInEnumEntries: Boolean = false,
    decls: List<Node.Decl> = listOf()
) = Node.Decl.Structured.Body(
    enumEntries = enumEntries,
    hasTrailingCommaInEnumEntries = hasTrailingCommaInEnumEntries,
    decls = decls
)

fun init(mods: Node.Modifiers? = null, block: Node.Expr.Block) = Node.Decl.Init(mods = mods, block = block)
fun function(
    mods: Node.Modifiers? = null,
    funKeyword: Node.Keyword.Fun = Node.Keyword.Fun(),
    typeParams: Node.TypeParams? = null,
    receiverTypeRef: Node.TypeRef? = null,
    name: Node.Expr.Name? = null,
    postTypeParams: Node.TypeParams? = null,
    params: Node.Decl.Func.Params? = null,
    typeRef: Node.TypeRef? = null,
    postMods: List<Node.PostModifier> = listOf(),
    body: Node.Decl.Func.Body? = null
) = Node.Decl.Func(
    mods = mods,
    funKeyword = funKeyword,
    typeParams = typeParams,
    receiverTypeRef = receiverTypeRef,
    name = name,
    postTypeParams = postTypeParams,
    params = params,
    typeRef = typeRef,
    postMods = postMods,
    body = body
)

fun functionParams(elements: List<Node.Decl.Func.Param> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Decl.Func.Params(elements = elements, trailingComma = trailingComma)

fun functionParams(vararg elements: Node.Decl.Func.Param) = functionParams(elements.toList())
fun functionParam(
    mods: Node.Modifiers? = null,
    valOrVar: Node.Keyword.ValOrVar? = null,
    name: Node.Expr.Name,
    typeRef: Node.TypeRef? = null,
    initializer: Node.Initializer? = null
) = Node.Decl.Func.Param(mods = mods, valOrVar = valOrVar, name = name, typeRef = typeRef, initializer = initializer)

fun functionBlockBody(block: Node.Expr.Block) = Node.Decl.Func.Body.Block(block = block)
fun functionExpressionBody(equals: Node.Keyword.Equal = Node.Keyword.Equal(), expr: Node.Expr) =
    Node.Decl.Func.Body.Expr(equals = equals, expr = expr)

fun property(
    mods: Node.Modifiers? = null,
    valOrVar: Node.Keyword.ValOrVar,
    typeParams: Node.TypeParams? = null,
    receiverTypeRef: Node.TypeRef? = null,
    variable: Node.Decl.Property.Variable,
    typeConstraints: Node.PostModifier.TypeConstraints? = null,
    initializer: Node.Initializer? = null,
    delegate: Node.Decl.Property.Delegate? = null,
    accessors: List<Node.Decl.Property.Accessor> = listOf()
) = Node.Decl.Property(
    mods = mods,
    valOrVar = valOrVar,
    typeParams = typeParams,
    receiverTypeRef = receiverTypeRef,
    variable = variable,
    typeConstraints = typeConstraints,
    initializer = initializer,
    delegate = delegate,
    accessors = accessors
)

fun variable(name: Node.Expr.Name, typeRef: Node.TypeRef? = null) =
    Node.Decl.Property.Variable.Single(name = name, typeRef = typeRef)

fun variable(vars: List<Node.Decl.Property.Variable.Single> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Decl.Property.Variable.Multi(vars = vars, trailingComma = trailingComma)

fun delegate(byKeyword: Node.Keyword.By = Node.Keyword.By(), expr: Node.Expr) =
    Node.Decl.Property.Delegate(byKeyword = byKeyword, expr = expr)

fun get(
    mods: Node.Modifiers? = null,
    getKeyword: Node.Keyword.Get = Node.Keyword.Get(),
    typeRef: Node.TypeRef? = null,
    postMods: List<Node.PostModifier> = listOf(),
    body: Node.Decl.Func.Body? = null
) = Node.Decl.Property.Accessor.Get(
    mods = mods,
    getKeyword = getKeyword,
    typeRef = typeRef,
    postMods = postMods,
    body = body
)

fun set(
    mods: Node.Modifiers? = null,
    setKeyword: Node.Keyword.Set = Node.Keyword.Set(),
    params: Node.Decl.Property.Accessor.Params? = null,
    postMods: List<Node.PostModifier> = listOf(),
    body: Node.Decl.Func.Body? = null
) = Node.Decl.Property.Accessor.Set(
    mods = mods,
    setKeyword = setKeyword,
    params = params,
    postMods = postMods,
    body = body
)

fun params(elements: List<Node.Decl.Func.Param> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Decl.Property.Accessor.Params(elements = elements, trailingComma = trailingComma)

fun params(vararg elements: Node.Decl.Func.Param) = params(elements.toList())
fun typeAlias(
    mods: Node.Modifiers? = null,
    name: Node.Expr.Name,
    typeParams: Node.TypeParams? = null,
    typeRef: Node.TypeRef
) = Node.Decl.TypeAlias(mods = mods, name = name, typeParams = typeParams, typeRef = typeRef)

fun secondaryConstructor(
    mods: Node.Modifiers? = null,
    constructorKeyword: Node.Keyword.Constructor = Node.Keyword.Constructor(),
    params: Node.Decl.Func.Params? = null,
    delegationCall: Node.Decl.SecondaryConstructor.DelegationCall? = null,
    block: Node.Expr.Block? = null
) = Node.Decl.SecondaryConstructor(
    mods = mods,
    constructorKeyword = constructorKeyword,
    params = params,
    delegationCall = delegationCall,
    block = block
)

fun delegationCall(target: Node.Decl.SecondaryConstructor.DelegationTarget, args: Node.ValueArgs? = null) =
    Node.Decl.SecondaryConstructor.DelegationCall(target = target, args = args)

fun enumEntry(
    mods: Node.Modifiers? = null,
    name: Node.Expr.Name,
    args: Node.ValueArgs? = null,
    body: Node.Decl.Structured.Body? = null
) = Node.EnumEntry(mods = mods, name = name, args = args, body = body)

fun initializer(equals: Node.Keyword.Equal = Node.Keyword.Equal(), expr: Node.Expr) =
    Node.Initializer(equals = equals, expr = expr)

fun typeParams(elements: List<Node.TypeParam> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.TypeParams(elements = elements, trailingComma = trailingComma)

fun typeParams(vararg elements: Node.TypeParam) = typeParams(elements.toList())
fun typeParam(mods: Node.Modifiers? = null, name: Node.Expr.Name, typeRef: Node.TypeRef? = null) =
    Node.TypeParam(mods = mods, name = name, typeRef = typeRef)

fun funcType(
    contextReceivers: Node.Type.Func.ContextReceivers? = null,
    receiver: Node.Type.Func.Receiver? = null,
    params: Node.Type.Func.Params? = null,
    typeRef: Node.TypeRef
) = Node.Type.Func(contextReceivers = contextReceivers, receiver = receiver, params = params, typeRef = typeRef)

fun contextReceivers(
    elements: List<Node.Type.Func.ContextReceiver> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.Type.Func.ContextReceivers(elements = elements, trailingComma = trailingComma)

fun contextReceivers(vararg elements: Node.Type.Func.ContextReceiver) = contextReceivers(elements.toList())
fun contextReceiver(typeRef: Node.TypeRef) = Node.Type.Func.ContextReceiver(typeRef = typeRef)
fun receiver(typeRef: Node.TypeRef) = Node.Type.Func.Receiver(typeRef = typeRef)
fun params(elements: List<Node.Type.Func.Param> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Type.Func.Params(elements = elements, trailingComma = trailingComma)

fun params(vararg elements: Node.Type.Func.Param) = params(elements.toList())
fun param(name: Node.Expr.Name? = null, typeRef: Node.TypeRef) = Node.Type.Func.Param(name = name, typeRef = typeRef)
fun simpleType(pieces: List<Node.Type.Simple.Piece> = listOf()) = Node.Type.Simple(pieces = pieces)
fun simpleType(vararg pieces: Node.Type.Simple.Piece) = simpleType(pieces.toList())
fun piece(name: Node.Expr.Name, typeArgs: Node.TypeArgs? = null) =
    Node.Type.Simple.Piece(name = name, typeArgs = typeArgs)

fun nullableType(
    lPar: Node.Keyword.LPar? = null,
    mods: Node.Modifiers? = null,
    type: Node.Type,
    rPar: Node.Keyword.RPar? = null
) = Node.Type.Nullable(lPar = lPar, mods = mods, type = type, rPar = rPar)

fun dynamicType(_unused_: Boolean = false) = Node.Type.Dynamic(_unused_ = _unused_)
fun typeArgs(elements: List<Node.TypeArg> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.TypeArgs(elements = elements, trailingComma = trailingComma)

fun typeArgs(vararg elements: Node.TypeArg) = typeArgs(elements.toList())
fun asterisk(asterisk: Node.Keyword.Asterisk = Node.Keyword.Asterisk()) = Node.TypeArg.Asterisk(asterisk = asterisk)
fun type(mods: Node.Modifiers? = null, typeRef: Node.TypeRef) = Node.TypeArg.Type(mods = mods, typeRef = typeRef)
fun typeRef(
    lPar: Node.Keyword.LPar? = null,
    mods: Node.Modifiers? = null,
    innerLPar: Node.Keyword.LPar? = null,
    innerMods: Node.Modifiers? = null,
    type: Node.Type? = null,
    innerRPar: Node.Keyword.RPar? = null,
    rPar: Node.Keyword.RPar? = null
) = Node.TypeRef(
    lPar = lPar,
    mods = mods,
    innerLPar = innerLPar,
    innerMods = innerMods,
    type = type,
    innerRPar = innerRPar,
    rPar = rPar
)

fun constructorCallee(type: Node.Type.Simple) = Node.ConstructorCallee(type = type)
fun valueArgs(elements: List<Node.ValueArg> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.ValueArgs(elements = elements, trailingComma = trailingComma)

fun valueArgs(vararg elements: Node.ValueArg) = valueArgs(elements.toList())
fun valueArg(name: Node.Expr.Name? = null, asterisk: Boolean = false, expr: Node.Expr) =
    Node.ValueArg(name = name, asterisk = asterisk, expr = expr)

fun container(expr: Node.Expr) = Node.Container(expr = expr)
fun ifExpression(
    ifKeyword: Node.Keyword.If = Node.Keyword.If(),
    condition: Node.Expr,
    body: Node.Container,
    elseBody: Node.Container? = null
) = Node.Expr.If(ifKeyword = ifKeyword, condition = condition, body = body, elseBody = elseBody)

fun tryExpression(
    block: Node.Expr.Block,
    catches: List<Node.Expr.Try.Catch> = listOf(),
    finallyBlock: Node.Expr.Block? = null
) = Node.Expr.Try(block = block, catches = catches, finallyBlock = finallyBlock)

fun catch(
    catchKeyword: Node.Keyword.Catch = Node.Keyword.Catch(),
    params: Node.Decl.Func.Params,
    block: Node.Expr.Block
) = Node.Expr.Try.Catch(catchKeyword = catchKeyword, params = params, block = block)

fun forExpression(
    forKeyword: Node.Keyword.For = Node.Keyword.For(),
    anns: List<Node.Modifier.AnnotationSet> = listOf(),
    loopParam: Node.Expr.Lambda.Param,
    loopRange: Node.Container,
    body: Node.Container
) = Node.Expr.For(forKeyword = forKeyword, anns = anns, loopParam = loopParam, loopRange = loopRange, body = body)

fun whileExpression(
    whileKeyword: Node.Keyword.While = Node.Keyword.While(),
    condition: Node.Container,
    body: Node.Container,
    doWhile: Boolean = false
) = Node.Expr.While(whileKeyword = whileKeyword, condition = condition, body = body, doWhile = doWhile)

fun binaryOpExpression(lhs: Node.Expr, oper: Node.Expr.BinaryOp.Oper, rhs: Node.Expr) =
    Node.Expr.BinaryOp(lhs = lhs, oper = oper, rhs = rhs)

fun infix(str: String) = Node.Expr.BinaryOp.Oper.Infix(str = str)
fun token(token: Node.Expr.BinaryOp.Token) = Node.Expr.BinaryOp.Oper.Token(token = token)
fun unaryOpExpression(expr: Node.Expr, oper: Node.Expr.UnaryOp.Oper, prefix: Boolean = false) =
    Node.Expr.UnaryOp(expr = expr, oper = oper, prefix = prefix)

fun oper(token: Node.Expr.UnaryOp.Token) = Node.Expr.UnaryOp.Oper(token = token)
fun typeOpExpression(lhs: Node.Expr, oper: Node.Expr.TypeOp.Oper, rhs: Node.TypeRef) =
    Node.Expr.TypeOp(lhs = lhs, oper = oper, rhs = rhs)

fun oper(token: Node.Expr.TypeOp.Token) = Node.Expr.TypeOp.Oper(token = token)
fun callable(recv: Node.Expr.DoubleColonRef.Recv? = null, name: Node.Expr.Name) =
    Node.Expr.DoubleColonRef.Callable(recv = recv, name = name)

fun doubleColonClassLiteral(recv: Node.Expr.DoubleColonRef.Recv? = null) = Node.Expr.DoubleColonRef.Class(recv = recv)
fun expr(expr: Node.Expr) = Node.Expr.DoubleColonRef.Recv.Expr(expr = expr)
fun type(type: Node.Type.Simple, questionMarks: List<Node.Keyword.Question> = listOf()) =
    Node.Expr.DoubleColonRef.Recv.Type(type = type, questionMarks = questionMarks)

fun parenExpression(expr: Node.Expr) = Node.Expr.Paren(expr = expr)
fun stringTmplExpression(elems: List<Node.Expr.StringTmpl.Elem> = listOf(), raw: Boolean = false) =
    Node.Expr.StringTmpl(elems = elems, raw = raw)

fun regular(str: String) = Node.Expr.StringTmpl.Elem.Regular(str = str)
fun shortTmpl(str: String) = Node.Expr.StringTmpl.Elem.ShortTmpl(str = str)
fun unicodeEsc(digits: String) = Node.Expr.StringTmpl.Elem.UnicodeEsc(digits = digits)
fun regularEsc(char: Char) = Node.Expr.StringTmpl.Elem.RegularEsc(char = char)
fun longTmpl(expr: Node.Expr) = Node.Expr.StringTmpl.Elem.LongTmpl(expr = expr)
fun constExpression(value: String, form: Node.Expr.Const.Form) = Node.Expr.Const(value = value, form = form)
fun lambdaExpression(params: Node.Expr.Lambda.Params? = null, body: Node.Expr.Lambda.Body? = null) =
    Node.Expr.Lambda(params = params, body = body)

fun params(elements: List<Node.Expr.Lambda.Param> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Expr.Lambda.Params(elements = elements, trailingComma = trailingComma)

fun params(vararg elements: Node.Expr.Lambda.Param) = params(elements.toList())
fun single(name: Node.Expr.Name, typeRef: Node.TypeRef? = null) =
    Node.Expr.Lambda.Param.Single(name = name, typeRef = typeRef)

fun multi(vars: Node.Expr.Lambda.Param.Multi.Variables, destructTypeRef: Node.TypeRef? = null) =
    Node.Expr.Lambda.Param.Multi(vars = vars, destructTypeRef = destructTypeRef)

fun variables(elements: List<Node.Expr.Lambda.Param.Single> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Expr.Lambda.Param.Multi.Variables(elements = elements, trailingComma = trailingComma)

fun variables(vararg elements: Node.Expr.Lambda.Param.Single) = variables(elements.toList())
fun body(statements: List<Node.Statement> = listOf()) = Node.Expr.Lambda.Body(statements = statements)
fun body(vararg statements: Node.Statement) = body(statements.toList())
fun thisExpression(label: String? = null) = Node.Expr.This(label = label)
fun superExpression(typeArg: Node.TypeRef? = null, label: String? = null) =
    Node.Expr.Super(typeArg = typeArg, label = label)

fun whenExpression(
    lPar: Node.Keyword.LPar = Node.Keyword.LPar(),
    expr: Node.Expr? = null,
    rPar: Node.Keyword.RPar = Node.Keyword.RPar(),
    entries: List<Node.Expr.When.Entry> = listOf()
) = Node.Expr.When(lPar = lPar, expr = expr, rPar = rPar, entries = entries)

fun whenEntryConds(
    conds: List<Node.Expr.When.Cond> = listOf(),
    trailingComma: Node.Keyword.Comma? = null,
    body: Node.Expr
) = Node.Expr.When.Entry.Conds(conds = conds, trailingComma = trailingComma, body = body)

fun whenEntryElse(elseKeyword: Node.Keyword.Else = Node.Keyword.Else(), body: Node.Expr) =
    Node.Expr.When.Entry.Else(elseKeyword = elseKeyword, body = body)

fun whenConditionExpr(expr: Node.Expr) = Node.Expr.When.Cond.Expr(expr = expr)
fun whenConditionIn(expr: Node.Expr, not: Boolean = false) = Node.Expr.When.Cond.In(expr = expr, not = not)
fun whenConditionIs(typeRef: Node.TypeRef, not: Boolean = false) = Node.Expr.When.Cond.Is(typeRef = typeRef, not = not)
fun objectExpression(decl: Node.Decl.Structured) = Node.Expr.Object(decl = decl)
fun throwExpression(expr: Node.Expr) = Node.Expr.Throw(expr = expr)
fun returnExpression(label: String? = null, expr: Node.Expr? = null) = Node.Expr.Return(label = label, expr = expr)
fun continueExpression(label: String? = null) = Node.Expr.Continue(label = label)
fun breakExpression(label: String? = null) = Node.Expr.Break(label = label)
fun collLitExpression(exprs: List<Node.Expr> = listOf(), trailingComma: Node.Keyword.Comma? = null) =
    Node.Expr.CollLit(exprs = exprs, trailingComma = trailingComma)

fun nameExpression(name: String) = Node.Expr.Name(name = name)
fun labeledExpression(label: String, expr: Node.Expr) = Node.Expr.Labeled(label = label, expr = expr)
fun annotatedExpression(anns: List<Node.Modifier.AnnotationSet> = listOf(), expr: Node.Expr) =
    Node.Expr.Annotated(anns = anns, expr = expr)

fun callExpression(
    expr: Node.Expr,
    typeArgs: Node.TypeArgs? = null,
    args: Node.ValueArgs? = null,
    lambdaArgs: List<Node.Expr.Call.LambdaArg> = listOf()
) = Node.Expr.Call(expr = expr, typeArgs = typeArgs, args = args, lambdaArgs = lambdaArgs)

fun lambdaArg(anns: List<Node.Modifier.AnnotationSet> = listOf(), label: String? = null, func: Node.Expr.Lambda) =
    Node.Expr.Call.LambdaArg(anns = anns, label = label, func = func)

fun arrayAccessExpression(
    expr: Node.Expr,
    indices: List<Node.Expr> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.Expr.ArrayAccess(expr = expr, indices = indices, trailingComma = trailingComma)

fun anonFuncExpression(func: Node.Decl.Func) = Node.Expr.AnonFunc(func = func)
fun propertyExpression(decl: Node.Decl.Property) = Node.Expr.Property(decl = decl)
fun blockExpression(statements: List<Node.Statement> = listOf()) = Node.Expr.Block(statements = statements)
fun blockExpression(vararg statements: Node.Statement) = blockExpression(statements.toList())
fun modifiers(elements: List<Node.Modifier> = listOf()) = Node.Modifiers(elements = elements)
fun modifiers(vararg elements: Node.Modifier) = modifiers(elements.toList())
fun annotationSet(
    atSymbol: Node.Keyword.At? = null,
    target: Node.Modifier.AnnotationSet.Target? = null,
    lBracket: Node.Keyword.LBracket? = null,
    anns: List<Node.Modifier.AnnotationSet.Annotation> = listOf(),
    rBracket: Node.Keyword.RBracket? = null
) = Node.Modifier.AnnotationSet(
    atSymbol = atSymbol,
    target = target,
    lBracket = lBracket,
    anns = anns,
    rBracket = rBracket
)

fun annotation(constructorCallee: Node.ConstructorCallee, args: Node.ValueArgs? = null) =
    Node.Modifier.AnnotationSet.Annotation(constructorCallee = constructorCallee, args = args)

fun literalModifier(keyword: Node.Modifier.Keyword) = Node.Modifier.Lit(keyword = keyword)
fun typeConstraints(
    whereKeyword: Node.Keyword.Where = Node.Keyword.Where(),
    constraints: Node.PostModifier.TypeConstraints.TypeConstraintList
) = Node.PostModifier.TypeConstraints(whereKeyword = whereKeyword, constraints = constraints)

fun typeConstraintList(elements: List<Node.PostModifier.TypeConstraints.TypeConstraint> = listOf()) =
    Node.PostModifier.TypeConstraints.TypeConstraintList(elements = elements)

fun typeConstraintList(vararg elements: Node.PostModifier.TypeConstraints.TypeConstraint) =
    typeConstraintList(elements.toList())

fun typeConstraint(anns: List<Node.Modifier.AnnotationSet> = listOf(), name: Node.Expr.Name, typeRef: Node.TypeRef) =
    Node.PostModifier.TypeConstraints.TypeConstraint(anns = anns, name = name, typeRef = typeRef)

fun contract(
    contractKeyword: Node.Keyword.Contract = Node.Keyword.Contract(),
    contractEffects: Node.PostModifier.Contract.ContractEffects
) = Node.PostModifier.Contract(contractKeyword = contractKeyword, contractEffects = contractEffects)

fun contractEffects(
    elements: List<Node.PostModifier.Contract.ContractEffect> = listOf(),
    trailingComma: Node.Keyword.Comma? = null
) = Node.PostModifier.Contract.ContractEffects(elements = elements, trailingComma = trailingComma)

fun contractEffects(vararg elements: Node.PostModifier.Contract.ContractEffect) = contractEffects(elements.toList())
fun contractEffect(expr: Node.Expr) = Node.PostModifier.Contract.ContractEffect(expr = expr)
fun valOrVar(token: Node.Keyword.ValOrVarToken) = Node.Keyword.ValOrVar(token = token)
fun declaration(token: Node.Keyword.DeclarationToken) = Node.Keyword.Declaration(token = token)
fun whitespace(text: String) = Node.Extra.Whitespace(text = text)
fun comment(text: String) = Node.Extra.Comment(text = text)
fun semicolon(text: String) = Node.Extra.Semicolon(text = text)
