function lamda = Karger(A)
G=graph(A);
E=G.Edges;
%plot(G)

%nodes are initially assigned to n different groups
%supernodes(i)=2 means node i is assigned to group 2
supernodes=1:length(A);
%while nodes are assigned to more than 2 different groups
while length(unique(supernodes))>2
    %pick a random edge
    row=randi(height(E));
    u=E{row,'EndNodes'}(1);
    v=E{row,'EndNodes'}(2);
    [supernodes,A]=merge(u,v,supernodes,A); 
    G=graph(A);
    E=G.Edges;
end
lamda=height(E);