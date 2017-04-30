#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


// Created by mmichal

void mainImage( out vec4 fragColor, in vec2 fragCoord )

{
    vec2 uv = fragCoord.xy;
	vec4 col = texture2D( iChannel0, uv );

	//Tritanomaly ("blue/yellow", 0.01% for males and females)
	vec3 c_r = vec3(0.96667, 0.03333, 0.0);
	vec3 c_g = vec3(0.0, 0.73333, 0.26667);
	vec3 c_b = vec3(0.0, 0.18333, 0.81667);

	vec3 rgb = vec3( dot(col.rgb ,c_r), dot(col.rgb ,c_g), dot(col.rgb ,c_b));

	fragColor = vec4(rgb,1.0);
}

void main() {
	mainImage(gl_FragColor, texCoord);
}
