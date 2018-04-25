function kalpa=minnodecut(A)
kalpa=min(sum(A));
G=graph(A);
for i=1:length(A)
    if conncomp(rmnode(G,i))>1
        kalpa=1;
        return
    end
end

for i=1:length(A)
    for j=i+1:length(A)
        if conncomp(rmnode(G,[i,j]))>1
            kalpa=2;
            return
        end
    end
end

for i=1:length(A)
    for j=i+1:length(A)
        for k=j+1:length(A)
            if conncomp(rmnode(G,[i,j,k]))>1
                kalpa=3;
                return
            end
        end
    end
end