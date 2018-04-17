function[prob_matrix] = computeProbability(G,x)

distance_matrix = computeDistance(G,x);
prob_matrix = zeros(1,size(distance_matrix,2));

for d = 1:size(distance_matrix,2)
    if(distance_matrix(1,d) == 0 || G(x,d) == 1)
        prob_matrix(1,d) = 0;
    else
        D = distance_matrix(1, d);
        if D == 1
            p = 0.5;
        elseif D == 2
            p = 0.3;
        else
            p = 0.1;
        end
        prob_matrix(1,d) = p;
    end
end